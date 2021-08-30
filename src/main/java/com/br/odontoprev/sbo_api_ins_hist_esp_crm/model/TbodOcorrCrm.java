package com.br.odontoprev.sbo_api_ins_hist_esp_crm.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(schema ="ADMPROD", name = "TBOD_OCORR_CRM")
@AllArgsConstructor
@NoArgsConstructor
public class TbodOcorrCrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TbodOcorrCrmId tbodOcorrCrmId;
    
}
