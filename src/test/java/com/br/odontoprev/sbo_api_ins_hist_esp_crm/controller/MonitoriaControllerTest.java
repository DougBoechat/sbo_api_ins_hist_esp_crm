package com.br.odontoprev.sbo_api_ins_hist_esp_crm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.ResponseMonitoria;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodFupOcorrRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodHistSitEspecialRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodOcorrCrmRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodOcorrenciaRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.service.MonitoriaService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import lombok.var;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest
public class MonitoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private MonitoriaController monitoriaController;

    @Autowired
    private MonitoriaService monitoriaService;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    public TbodOcorrenciaRepository tbodOcorrenciaRepository;

    @Autowired
    private TbodFupOcorrRepository tbodFupOcorrRepository;

    @Autowired
    private TbodOcorrCrmRepository tbodOcorrCrmRepository;

    @Autowired
    private TbodHistSitEspecialRepository tbodHistSitEspecialRepository;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Before
    public void init(){
        this.monitoriaService = new MonitoriaService(tbodOcorrenciaRepository, tbodFupOcorrRepository, tbodOcorrCrmRepository, tbodHistSitEspecialRepository, restTemplate);
        ReflectionTestUtils.setField(monitoriaService, "contextPath", contextPath);
    }

    @Test
    public void testeSucesso() throws Exception {

        String body = "{\"status\": \"UP\",\"components\": {\"db\": {\"status\": \"UP\",\"details\": {\"database\": \"Oracle\",\"validationQuery\": \"isValid()\"}},\"diskSpace\": {\"status\": \"UP\",\"details\": {\"total\": 255450935296,\"free\": 135453855744,\"threshold\": 10485760,\"exists\": true}},\"ping\": {\"status\": \"UP\"}}}";

        ResponseEntity<String> responseEntity = new ResponseEntity<String>(body, HttpStatus.OK);

        ResponseMonitoria result = new ResponseMonitoria();
        result.setStatusDB("UP");
        result.setStatusPing("UP");
        result.setStatusSistema("UP");
        result.setStatusdiskSpace("UP");
        result.setResumo("teste");

        MockHttpServletRequest requestMock = new MockHttpServletRequest();
        requestMock.setProtocol("http");
        requestMock.setServerPort(-1);
        requestMock.setRequestURI("/monitoria");

        URL url = new URL(requestMock.getRequestURL().toString());

        BDDMockito.given(monitoriaService.runActuator(url))
        .willReturn(responseEntity);

        var request = MockMvcRequestBuilders.get("/monitoria");
        var response = mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void testeNotFound() throws Exception{
        var request = MockMvcRequestBuilders.get("/monitoria1");
        var response = mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
    
}
