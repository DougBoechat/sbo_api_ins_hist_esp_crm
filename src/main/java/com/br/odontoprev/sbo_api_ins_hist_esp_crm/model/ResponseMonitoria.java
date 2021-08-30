package com.br.odontoprev.sbo_api_ins_hist_esp_crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMonitoria {

    private String statusSistema;
    private String statusdiskSpace;
    private String statusPing;
    private String statusDB;
    private String Resumo;
    
}
