package com.br.odontoprev.sbo_api_ins_hist_esp_crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(schema ="ADMPROD", name = "TBOD_HIST_SIT_ESPECIAL")
@AllArgsConstructor
@NoArgsConstructor
public class TbodHistSitEspecial implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TbodHistSitEspecialId tbodHistSitEspecialId;
    @Column(name = "ID_SIT_ESPECIAL")
    private String idSitEspecial;
    
}
