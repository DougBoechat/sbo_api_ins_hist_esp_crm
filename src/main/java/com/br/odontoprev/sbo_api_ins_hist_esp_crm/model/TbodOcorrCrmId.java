package com.br.odontoprev.sbo_api_ins_hist_esp_crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@Data
@NoArgsConstructor
public class TbodOcorrCrmId implements Serializable{

    private static final long serialVersionUID = 1L;

    @Column(name = "ID_OCORR_CRM")
    private Integer idOcorrCrm;
    @Column(name = "ID_OCORR_DCMS")
    private Long idOcorrDcms;
    
}
