package com.br.odontoprev.sbo_api_ins_hist_esp_crm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.OcorrenciaSituacaoEspecial;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrencia;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrenciaId;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.service.OcorrenciaService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import lombok.var;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest
public class OcorrenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private OcorrenciaController ocorrenciaController;

    @MockBean
    private OcorrenciaService ocorrenciaService;

    private String bodySucess = "{\"idOcorrencia\": \"A\", \"codDentista\": \"PIN201\", \"tipoOcorr\": 123, \"motivoOcorr\": 5678, \"descOcorr\": \"teste\", \"dtOcorrenciaIni\": \"2016-03-16T13:56:39.492\", \"dtOcorrenciaFim\": \"2016-03-16T13:56:39.492\", \"contatoOcorr\": \"Arthur\", \"telContato\": \"21 22222222\", \"odpResp\": \"teste 2\", \"nrOcorrencia\": \"12345678\"}";
    private OcorrenciaSituacaoEspecial bodyOcorrencia = new OcorrenciaSituacaoEspecial("A", "PIN201", 123, 5678, "teste", LocalDateTime.of(2016, 3, 16, 13, 56, 39, 492), LocalDateTime.of(2016, 3, 16, 13, 56, 39, 492), "Arthur", "21 22222222", "teste 2", 12345678);

    @Test
    public void testeSucesso() throws Exception {

        TbodOcorrencia tbodCountOcorrIaCrmList = geraTbodOcorrencia();

        BDDMockito.given(ocorrenciaService.gravaOcorrencia(any()))
        .willReturn(tbodCountOcorrIaCrmList);

        var request = MockMvcRequestBuilders.post("/ocorrencia/insert").contentType(MediaType.APPLICATION_JSON).content(bodySucess);
        var response = mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void testeNotFound() throws Exception{
        var request = MockMvcRequestBuilders.post("/ocorrencia/insert1");
        var response = mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void testeErroBadRequest() throws Exception{

        var request = MockMvcRequestBuilders.post("/ocorrencia/insert");
        var response = mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testeErroInterno() throws Exception{


        Exception exception = new RuntimeException("Erro interno");
        BDDMockito.given(ocorrenciaService.gravaOcorrencia(any())).willThrow(exception);

        var request = MockMvcRequestBuilders.post("/ocorrencia/insert").contentType(MediaType.APPLICATION_JSON).content(bodySucess);
        var response = mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isInternalServerError())
        .andReturn().getResponse();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    private TbodOcorrencia geraTbodOcorrencia(){

        TbodOcorrencia tbodOcorrencia = new TbodOcorrencia();
        TbodOcorrenciaId id = new TbodOcorrenciaId();
        id.setNrOcorrencia(12345678L);
        id.setIdOcorrencia(bodyOcorrencia.getIdOcorrencia());
        tbodOcorrencia.setTbodOcorrenciaId(id);
        tbodOcorrencia.setCdCirDentista(bodyOcorrencia.getCodDentista());
        tbodOcorrencia.setCdTipoOcorr(bodyOcorrencia.getTipoOcorr());
        tbodOcorrencia.setCdMotivoOcorr(bodyOcorrencia.getMotivoOcorr());
        tbodOcorrencia.setDsOcorrencia(bodyOcorrencia.getDescOcorr());
        tbodOcorrencia.setDtOcorrencia(bodyOcorrencia.getDtOcorrenciaIni());
        tbodOcorrencia.setIdSituacao("Atendido");
        tbodOcorrencia.setNmContato(bodyOcorrencia.getContatoOcorr());
        tbodOcorrencia.setDsFoneContato(bodyOcorrencia.getTelContato());

        return tbodOcorrencia;
    }




}
