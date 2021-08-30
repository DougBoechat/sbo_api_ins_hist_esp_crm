package com.br.odontoprev.sbo_api_ins_hist_esp_crm.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcorrenciaSituacaoEspecial {

    private String idOcorrencia;
    private String codDentista;
    private Integer tipoOcorr;
    private Integer motivoOcorr;
    private String descOcorr;
    private LocalDateTime dtOcorrenciaIni;
    private LocalDateTime dtOcorrenciaFim;
    private String contatoOcorr;
    private String telContato;
    private String odpResp;
    private Integer nrOcorrencia;
}
