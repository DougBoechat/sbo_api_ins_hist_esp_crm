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
@Table(schema ="ADMPROD", name = "TBOD_OCORRENCIA")
@AllArgsConstructor
@NoArgsConstructor
// @SequenceGenerator(name = "seq_pessoa", sequenceName = "seuSchema.seq_pessoa", allocationSize = 1)
public class TbodOcorrencia implements Serializable {

    private static final long serialVersionUID = 1L;

 
    @EmbeddedId
    private TbodOcorrenciaId tbodOcorrenciaId;
    @Column(name = "CD_CIR_DENTISTA")
    private String cdCirDentista;
    @Column(name = "CD_TIPO_OCORR")
    private Integer cdTipoOcorr;
    @Column(name = "CD_MOTIVO_OCORR")
    private Integer cdMotivoOcorr;
    @Column(name = "DS_OCORRENCIA")
    private String dsOcorrencia;
    @Column(name = "DT_OCORRENCIA")
    private LocalDateTime dtOcorrencia;
    @Column(name = "ID_SITUACAO")
    private String idSituacao;
    @Column(name = "NM_CONTATO")
    private String nmContato;
    @Column(name = "DS_FONE_CONTATO")
    private String dsFoneContato;


    
}
