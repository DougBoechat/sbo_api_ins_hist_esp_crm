package com.br.odontoprev.sbo_api_ins_hist_esp_crm.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ResourceAlreadyExistsException extends DataIntegrityViolationException {

    private static final long serialVersionUID = 1L;
    
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
    
}
