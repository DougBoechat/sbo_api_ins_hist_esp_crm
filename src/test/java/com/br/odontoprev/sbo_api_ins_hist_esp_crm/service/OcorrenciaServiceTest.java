package com.br.odontoprev.sbo_api_ins_hist_esp_crm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.OcorrenciaSituacaoEspecial;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrencia;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrenciaId;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodFupOcorrRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodHistSitEspecialRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodOcorrCrmRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodOcorrenciaRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.VwodCirDentistaTodosRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest
public class OcorrenciaServiceTest {

    @Value("${ocorrencia.tipo.depara}")
    private String deParaTipoOcorr;
    @Value("${ocorrencia.motivo.depara}")
    private String deParaMotivoOcorr;
    @Value("${ocorrencia.perfil.origem}")
    private int ocorrenciaPerfilOrigem;
    @Value("${ocorrencia.motivo.situacaoespecial}")
    private String ocorrenciasSituacaoEspecial;
    @Value("${ocorrencia.id.situacao}")
    private String ocorrenciaIdSituacao;

    private OcorrenciaService ocorrenciaService;

    @MockBean
    private TbodOcorrenciaRepository tbodOcorrenciaRepository;

    @MockBean
    private TbodFupOcorrRepository tbodFupOcorrRepository;

    @MockBean
    private TbodOcorrCrmRepository tbodOcorrCrmRepository;

    @MockBean
    private TbodHistSitEspecialRepository tbodHistSitEspecialRepository;

    @MockBean
    private VwodCirDentistaTodosRepository vwodCirDentistaTodosRepository;

    private OcorrenciaSituacaoEspecial bodyOcorrencia = new OcorrenciaSituacaoEspecial("A", "PIN201", 123, 5678, "teste", LocalDateTime.of(2016, 3, 16, 13, 56, 39, 492), LocalDateTime.of(2016, 3, 16, 13, 56, 39, 492), "Arthur", "21 22222222", "teste 2", 12345678);

    @Before
    public void init(){
        this.ocorrenciaService = new OcorrenciaService(tbodOcorrenciaRepository, tbodFupOcorrRepository, tbodOcorrCrmRepository, tbodHistSitEspecialRepository, vwodCirDentistaTodosRepository);
        ReflectionTestUtils.setField(ocorrenciaService, "deParaTipoOcorr", deParaTipoOcorr);
        ReflectionTestUtils.setField(ocorrenciaService, "deParaMotivoOcorr", deParaMotivoOcorr);
        ReflectionTestUtils.setField(ocorrenciaService, "ocorrenciaPerfilOrigem", ocorrenciaPerfilOrigem);
        ReflectionTestUtils.setField(ocorrenciaService, "ocorrenciasSituacaoEspecial", ocorrenciasSituacaoEspecial);
        ReflectionTestUtils.setField(ocorrenciaService, "ocorrenciaIdSituacao", ocorrenciaIdSituacao);
    }
    @Test
    public void gravaOcorrenciaComSucesso() throws Exception{

        TbodOcorrencia tbodOcorrencia = geraTbodOcorrencia();

        BDDMockito.given(ocorrenciaService.persistTbodOcorrencia(bodyOcorrencia))
        .willReturn(tbodOcorrencia);

        TbodOcorrencia tbodOcorrenciaResponse = ocorrenciaService.gravaOcorrencia(bodyOcorrencia);

        assertEquals(tbodOcorrenciaResponse.getTbodOcorrenciaId().getNrOcorrencia(), tbodOcorrencia.getTbodOcorrenciaId().getNrOcorrencia());

    }

    private TbodOcorrencia geraTbodOcorrencia(){

        TbodOcorrencia tbodOcorrencia = new TbodOcorrencia();
        TbodOcorrenciaId id = new TbodOcorrenciaId();
        id.setNrOcorrencia(0L);
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
