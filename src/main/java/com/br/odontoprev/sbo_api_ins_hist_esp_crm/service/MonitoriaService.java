package com.br.odontoprev.sbo_api_ins_hist_esp_crm.service;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.ResponseMonitoria;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodFupOcorr;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodHistSitEspecial;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrCrm;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrencia;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodFupOcorrRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodHistSitEspecialRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodOcorrCrmRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodOcorrenciaRepository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonitoriaService {

    private long nrOcorrencia = 0L;

    public final TbodOcorrenciaRepository tbodOcorrenciaRepository;

    private final TbodFupOcorrRepository tbodFupOcorrRepository;

    private final TbodOcorrCrmRepository tbodOcorrCrmRepository;

    private final TbodHistSitEspecialRepository tbodHistSitEspecialRepository;

    private final RestTemplate restTemplate;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public ResponseMonitoria getMonitoria(HttpServletRequest request) {

        try {

            nrOcorrencia = tbodOcorrenciaRepository.findMaxNrOcorrencia();

            if (nrOcorrencia == 0L) {
                throw new Exception(
                        "A tabela TBOD_OCORRENCIA pode estar vazia, ou ocorreu falha de comunicacao com uma ou mais tabelas, ou por dados inconsistentes entre as tabelas.");
            }

            TbodOcorrencia tbodOcorrencia = tbodOcorrenciaRepository.findByNrOcorrencia(nrOcorrencia);
            TbodFupOcorr tbodFupOcorr = tbodFupOcorrRepository.findByNrOcorrencia(nrOcorrencia);
            TbodOcorrCrm tbodOcorrCrm = tbodOcorrCrmRepository.findByNrOcorrencia(nrOcorrencia);
            TbodHistSitEspecial tbodHistSitEspecial = tbodHistSitEspecialRepository.findByNrOcorrencia(nrOcorrencia);

            assertNotNull(tbodOcorrencia);
            assertNotNull(tbodFupOcorr);
            assertNotNull(tbodOcorrCrm);
            assertNotNull(tbodHistSitEspecial);

            String json;
            URL url = new URL(request.getRequestURL().toString());
            try {
                ResponseEntity<String> responseEntity = runActuator(url);
                json = responseEntity.getBody();

            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Um ou mais componentes do sistema estao indisponiveis. Para mais informacoes, acesse a URL /actuator/health: ",
                        e);
            }

            JSONObject responseBody = new JSONObject(json);
            log.info("status sistema: " + responseBody.get("status").toString());// status sistema

            JSONObject jsonComponents = responseBody.getJSONObject("components");

            JSONObject jsonDiskSpace = jsonComponents.getJSONObject("diskSpace");
            log.info("status diskSpace: " + jsonDiskSpace.get("status").toString());// status diskSpace

            JSONObject jsonPing = jsonComponents.getJSONObject("ping");
            log.info("status Ping: " + jsonPing.get("status").toString());// status Ping

            JSONObject jsonDb = jsonComponents.getJSONObject("db");
            log.info("status DB: " + jsonDb.get("status").toString());// status DB

            if ("UP".equals(responseBody.getString("status")) && "UP".equals(jsonDiskSpace.getString("status"))
                    && "UP".equals(jsonPing.getString("status")) && "UP".equals(jsonDb.getString("status"))) {

                return geraRetorno(responseBody.getString("status"), jsonDiskSpace.getString("status"),
                        jsonPing.getString("status"), jsonDb.getString("status"));

            } else {

                throw new Exception(
                        "Um ou mais componentes do sistema estao indisponiveis. Para mais informacoes, acesse a URL /actuator/health.");

            }
        } catch (Error | Exception ex) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "O erro pode ter ocorrido por falha de comunicacao com uma ou mais tabelas, ou por dados inconsistentes entre as tabelas",
                    ex);

        }

    }

    public ResponseEntity<String> runActuator(URL url) {

        String req = url.getProtocol() + "://" + url.getAuthority() + contextPath + "/actuator/health";
        return restTemplate.getForEntity(req, String.class);

    }

    public ResponseMonitoria geraRetorno(String statusSistema, String statusdiskSpace, String statusPing,
            String statusDB) {
        String Resumo = "Monitoria da API de inscricao executado com sucesso utilizando a ocorrencia " + nrOcorrencia
                + " para testes.";
        ResponseMonitoria jsonRetorno = new ResponseMonitoria(statusSistema, statusdiskSpace, statusPing, statusDB,
                Resumo);
        return jsonRetorno;
    }

}
