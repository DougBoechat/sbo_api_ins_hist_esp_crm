package com.br.odontoprev.sbo_api_ins_hist_esp_crm.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(schema ="ADMPROD", name = "TBOD_FUP_OCORR")
@AllArgsConstructor
@NoArgsConstructor
public class TbodFupOcorr implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TbodFupOcorrId tbodFupOcorrId;
    @Column(name = "CD_PERFIL_ORIGEM")
    private Integer cdPerfilOrigem;
    @Column(name = "CD_PERFIL_DESTINO")
    private Integer cdPerfilDestino;
    @Column(name = "DS_FUP_OCORR")
    private String dsFupOcorr;
    @Column(name = "DT_INICIO_FUP_OCORR")
    private LocalDateTime dtInicioFupOcorr;
    @Column(name = "DT_FIM_FUP_OCORR")
    private LocalDateTime dtFimFupOcorr;
    @Column(name = "CD_USUARIO_RESP")
    private String cdUsuarioResp;
    
}
