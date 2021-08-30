package com.br.odontoprev.sbo_api_ins_hist_esp_crm.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpServletRequest;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.ResponseMonitoria;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.service.MonitoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "Api para monitoramento e testes integrados da aplicação.")
@Slf4j
public class MonitoriaController {

    @Autowired
    private MonitoriaService monitoriaService;

    @GetMapping("/monitoria")
    public ResponseEntity<ResponseMonitoria> monitoria(HttpServletRequest request) {
        
        LocalDateTime inicio = LocalDateTime.now();
        ResponseMonitoria response = monitoriaService.getMonitoria(request);
        log.info("Monitoria da API de inscricao executado com sucesso em {} milisegundos.", ChronoUnit.MILLIS.between(inicio, LocalDateTime.now()));
        return ResponseEntity.ok(response);

    }
}
