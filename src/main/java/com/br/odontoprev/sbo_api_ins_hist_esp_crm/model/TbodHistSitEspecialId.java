package com.br.odontoprev.sbo_api_ins_hist_esp_crm.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@Data
@NoArgsConstructor
public class TbodHistSitEspecialId implements Serializable{

    private static final long serialVersionUID = 1L;

    @Column(name = "NR_CGCCPF")
    private String nrCgccpf;
    @Column(name = "DT_SIT_ESPECIAL")
    private LocalDateTime dtSitEspecial;
    @Column(name = "NR_OCORRENCIA")
    private Long nrOcorrencia;
    
}
