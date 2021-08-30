package com.br.odontoprev.sbo_api_ins_hist_esp_crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@Data
@NoArgsConstructor
@SequenceGenerator(name = "seq_nr_ocorrencia",sequenceName = "nova_ocorrencia", allocationSize = 1)
public class TbodOcorrenciaId implements Serializable{

    private static final long serialVersionUID = 1L;

    
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nr_ocorrencia")
	@Column(name = "NR_OCORRENCIA", unique = true, nullable = false, insertable = true, updatable = true)
    private Long nrOcorrencia;
    @Column(name = "ID_OCORRENCIA")
    private String idOcorrencia;
    
}
