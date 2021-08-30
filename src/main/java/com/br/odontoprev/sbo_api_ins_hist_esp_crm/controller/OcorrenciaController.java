package com.br.odontoprev.sbo_api_ins_hist_esp_crm.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.OcorrenciaSituacaoEspecial;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrencia;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.service.OcorrenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ocorrencia")
@Api(value = "Api para persistencia de ocorrências originadas no CRM na base do DCMS")
@Slf4j
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @PostMapping("/insert")
    @ApiOperation(value = "Grava dados da ocorrência na base do DCMS.")
    public ResponseEntity<TbodOcorrencia> postController(@RequestBody OcorrenciaSituacaoEspecial ocorrSitEsp) {
        LocalDateTime inicio = LocalDateTime.now();

        TbodOcorrencia tbodOcorrencia = ocorrenciaService.gravaOcorrencia(ocorrSitEsp);
        log.info("Ocorrência gravada com sucesso. Numero da ocorrencia no DCMS: {}", tbodOcorrencia.getTbodOcorrenciaId().getNrOcorrencia());
        log.info("Processo executado com sucesso em {} segundos.", ChronoUnit.SECONDS.between(inicio, LocalDateTime.now()));
        return ResponseEntity.ok(tbodOcorrencia);

    }

}
