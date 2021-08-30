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
public class TbodFupOcorrId implements Serializable{

    private static final long serialVersionUID = 1L;

    @Column(name = "NR_OCORRENCIA")
    private Long nrOcorrencia;
    @Column(name = "ID_OCORRENCIA")
    private String idOcorrencia;
    @Column(name = "NR_SEQUENCIAL")
    private int nrSequencial;
    
}
