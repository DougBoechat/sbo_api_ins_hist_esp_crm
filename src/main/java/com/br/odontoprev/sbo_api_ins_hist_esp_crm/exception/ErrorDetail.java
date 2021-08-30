package com.br.odontoprev.sbo_api_ins_hist_esp_crm.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorDetail {

    private int statusCode;
    private String message;
    private String path;
    
}
