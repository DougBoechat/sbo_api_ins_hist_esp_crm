package com.br.odontoprev.sbo_api_ins_hist_esp_crm.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.ResponseMonitoria;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodFupOcorrRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodHistSitEspecialRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodOcorrCrmRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodOcorrenciaRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest
@WebAppConfiguration
public class MonitoriaServiceTest {

    private MonitoriaService monitoriaService;

    @MockBean
    private HttpServletRequest request;

    @Autowired
    public TbodOcorrenciaRepository tbodOcorrenciaRepository;

    @Autowired
    private TbodFupOcorrRepository tbodFupOcorrRepository;

    @Autowired
    private TbodOcorrCrmRepository tbodOcorrCrmRepository;

    @Autowired
    private TbodHistSitEspecialRepository tbodHistSitEspecialRepository;

    @MockBean
    private RestTemplate restTemplate;


    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Before
    public void init(){
        this.monitoriaService = new MonitoriaService(tbodOcorrenciaRepository, tbodFupOcorrRepository, tbodOcorrCrmRepository, tbodHistSitEspecialRepository, restTemplate);
        ReflectionTestUtils.setField(monitoriaService, "contextPath", contextPath);
    }

    @Test 
    public void testSucess() throws MalformedURLException{

        String body = "{\"status\": \"UP\",\"components\": {\"db\": {\"status\": \"UP\",\"details\": {\"database\": \"Oracle\",\"validationQuery\": \"isValid()\"}},\"diskSpace\": {\"status\": \"UP\",\"details\": {\"total\": 255450935296,\"free\": 135453855744,\"threshold\": 10485760,\"exists\": true}},\"ping\": {\"status\": \"UP\"}}}";

        ResponseEntity<String> responseEntity = new ResponseEntity<String>(body, HttpStatus.OK);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setProtocol("http");
        request.setServerPort(8080);
        request.setRequestURI("/teste");

        URL url = new URL(request.getRequestURL().toString());

        BDDMockito.given(monitoriaService.runActuator(url))
        .willReturn(responseEntity);

        ResponseMonitoria result = monitoriaService.getMonitoria(request);
        assertNotNull(result);


    }
    
}
