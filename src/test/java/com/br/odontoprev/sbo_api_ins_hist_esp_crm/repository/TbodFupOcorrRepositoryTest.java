package com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository;

import java.time.LocalDateTime;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodFupOcorr;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodFupOcorrId;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TbodFupOcorrRepositoryTest {

    @Autowired
    private TbodFupOcorrRepository tbodFupOcorrRepository;

    @Test
    public void saveTbodFupOcorr() {
        TbodFupOcorr tbodFupOcorr = new TbodFupOcorr();
        tbodFupOcorr.setTbodFupOcorrId(new TbodFupOcorrId(12345678L, "A", 1));
        tbodFupOcorr.setCdPerfilOrigem(1234);
        tbodFupOcorr.setCdPerfilDestino(null);
        tbodFupOcorr.setDsFupOcorr("TESTE");
        tbodFupOcorr.setDtInicioFupOcorr(LocalDateTime.of(2016, 3, 16, 13, 56, 39, 492));
        tbodFupOcorr.setDtFimFupOcorr(LocalDateTime.of(2016, 3, 16, 13, 56, 39, 492));
        tbodFupOcorr.setCdUsuarioResp("Arilson");

        TbodFupOcorr savedTbodFupOcorr = tbodFupOcorrRepository.save(tbodFupOcorr);

        
        Assertions.assertThat(savedTbodFupOcorr.getTbodFupOcorrId().getNrOcorrencia()).isNotNull();
    }
    
}
