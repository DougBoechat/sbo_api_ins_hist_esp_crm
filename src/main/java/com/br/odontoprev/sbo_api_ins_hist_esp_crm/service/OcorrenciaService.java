package com.br.odontoprev.sbo_api_ins_hist_esp_crm.service;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.exception.ResourceAlreadyExistsException;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.exception.ResourceException;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.OcorrenciaSituacaoEspecial;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodFupOcorr;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodFupOcorrId;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodHistSitEspecial;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodHistSitEspecialId;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrCrm;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrCrmId;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrencia;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrenciaId;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodFupOcorrRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodHistSitEspecialRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodOcorrCrmRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.TbodOcorrenciaRepository;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository.VwodCirDentistaTodosRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OcorrenciaService {

    private static final String DESCRICAO_OCORRENCIA = "IMPORTADO PELO INTEGRADOR CRM OCORR HIST SIT ESPECIAL sbo-crm-integ-ocorr-sit-esp ";

    private final TbodOcorrenciaRepository tbodOcorrenciaRepository;

    private final TbodFupOcorrRepository tbodFupOcorrRepository;

    private final TbodOcorrCrmRepository tbodOcorrCrmRepository;

    private final TbodHistSitEspecialRepository tbodHistSitEspecialRepository;

    private final VwodCirDentistaTodosRepository vwodCirDentistaTodosRepository;

    @Value("${ocorrencia.tipo.depara}")
    private String deParaTipoOcorr;
    @Value("${ocorrencia.motivo.depara}")
    private String deParaMotivoOcorr;
    @Value("${ocorrencia.perfil.origem}")
    private int ocorrenciaPerfilOrigem;
    @Value("${ocorrencia.motivo.situacaoespecial}")
    private String ocorrenciasSituacaoEspecial;
    @Value("${ocorrencia.id.situacao}")
    private String ocorrenciaIdSituacao;

    private Long sequenceTbodOcorrencia;
    private Boolean situacaoEspecial;

    public TbodOcorrencia gravaOcorrencia(OcorrenciaSituacaoEspecial ocorrSitEsp) {
        sequenceTbodOcorrencia = tbodOcorrenciaRepository.getNextValSequence();
        log.info("Sequence gerada: {}", sequenceTbodOcorrencia);

        TbodOcorrencia tbodOcorrencia = persistTbodOcorrencia(ocorrSitEsp);
        persistTbodFupOcorr(ocorrSitEsp);
        persistTbodOcorrCrm(ocorrSitEsp);
        persistTbodHistSitEspecial(ocorrSitEsp);

        return tbodOcorrencia;
        
    }

    public TbodOcorrencia persistTbodOcorrencia(OcorrenciaSituacaoEspecial ocorrSitEsp) {

        log.info("Persistindo tabela TBOD_OCORRENCIA...");
        
        TbodOcorrencia tbodOcorrencia = new TbodOcorrencia();
        TbodOcorrenciaId id = new TbodOcorrenciaId();
        id.setNrOcorrencia(sequenceTbodOcorrencia);
        id.setIdOcorrencia(ocorrSitEsp.getIdOcorrencia());
        tbodOcorrencia.setTbodOcorrenciaId(id);
        tbodOcorrencia.setCdCirDentista(ocorrSitEsp.getCodDentista());
        tbodOcorrencia.setCdTipoOcorr(propertieFactory(deParaTipoOcorr, ocorrSitEsp.getTipoOcorr(), false));
        tbodOcorrencia.setCdMotivoOcorr(propertieFactory(deParaMotivoOcorr, ocorrSitEsp.getMotivoOcorr(), true));
        tbodOcorrencia.setDsOcorrencia(DESCRICAO_OCORRENCIA.concat(ocorrSitEsp.getDescOcorr()));
        tbodOcorrencia.setDtOcorrencia(ocorrSitEsp.getDtOcorrenciaIni());
        tbodOcorrencia.setIdSituacao(ocorrenciaIdSituacao);
        tbodOcorrencia.setNmContato(ocorrSitEsp.getContatoOcorr());
        tbodOcorrencia.setDsFoneContato(ocorrSitEsp.getTelContato());

        tbodOcorrenciaRepository.save(tbodOcorrencia);

        return tbodOcorrencia;
    }

    private void persistTbodFupOcorr(OcorrenciaSituacaoEspecial ocorrSitEsp) {

        log.info("Persistindo tabela TBOD_FUP_OCORR...");

        Integer nrSequencial = tbodFupOcorrRepository.findLastNrSequencial(sequenceTbodOcorrencia, ocorrSitEsp.getIdOcorrencia());
        if (nrSequencial == null){
            nrSequencial = 1;
        } else {
            nrSequencial += 1;
        }
        
        TbodFupOcorr tbodFupOcorr = new TbodFupOcorr();
        tbodFupOcorr.setTbodFupOcorrId(new TbodFupOcorrId(sequenceTbodOcorrencia, ocorrSitEsp.getIdOcorrencia(), nrSequencial));
        tbodFupOcorr.setCdPerfilOrigem(ocorrenciaPerfilOrigem);
        tbodFupOcorr.setCdPerfilDestino(null);
        tbodFupOcorr.setDsFupOcorr(DESCRICAO_OCORRENCIA.concat(ocorrSitEsp.getDescOcorr()));
        tbodFupOcorr.setDtInicioFupOcorr(ocorrSitEsp.getDtOcorrenciaIni());
        tbodFupOcorr.setDtFimFupOcorr(ocorrSitEsp.getDtOcorrenciaFim());
        tbodFupOcorr.setCdUsuarioResp(ocorrSitEsp.getOdpResp());

        tbodFupOcorrRepository.save(tbodFupOcorr);
    }

    private void persistTbodOcorrCrm(OcorrenciaSituacaoEspecial ocorrSitEsp) {

        log.info("Persistindo tabela TBOD_OCORR_CRM...");

        TbodOcorrCrm tbodOcorrCrm = new TbodOcorrCrm(new TbodOcorrCrmId(ocorrSitEsp.getNrOcorrencia(), sequenceTbodOcorrencia));

        try{
            tbodOcorrCrmRepository.save(tbodOcorrCrm);
        } catch (DataIntegrityViolationException | ConstraintViolationException e){
            log.info(e.getCause().getCause().getMessage().trim());
            throw new ResourceAlreadyExistsException(e.getCause().getCause().getMessage().trim());
        } catch (Exception e){
            log.info(e.getCause().getCause().getLocalizedMessage());
            throw new ResourceException(e.getCause().getMessage().trim());
        }
        

    }

    private void persistTbodHistSitEspecial(OcorrenciaSituacaoEspecial ocorrSitEsp) {

        log.info("Persistindo tabela TBOD_HIST_SIT_ESPECIAL...");

        String nrCgcCpf = vwodCirDentistaTodosRepository.findNrCgccpfByNrCgccpf(ocorrSitEsp.getCodDentista());

        TbodHistSitEspecial tbodHistSitEspecial = new TbodHistSitEspecial();
        tbodHistSitEspecial.setTbodHistSitEspecialId(new TbodHistSitEspecialId(nrCgcCpf, ocorrSitEsp.getDtOcorrenciaFim(), sequenceTbodOcorrencia));
        tbodHistSitEspecial.setIdSitEspecial(situacaoEspecial ? "S" : "N");

        tbodHistSitEspecialRepository.save(tbodHistSitEspecial);

    }

    private Integer propertieFactory(String listaProperties, Integer valor, Boolean isMotivo){
        String[] propertiesArray = listaProperties.split(",");
        Integer dePara = null;

        for (String propertie : propertiesArray){
            String[] propertieArray = propertie.split("/");
            if (valor == Integer.parseInt(propertieArray[0])){
                dePara = Integer.parseInt(propertieArray[1]);
            }
        }

        if (isMotivo){
            situacaoEspecial = isSituacaoEspecial(dePara);
        }
        
        return dePara;
    }

    private Boolean isSituacaoEspecial(Integer dePara){
        String[] propertiesArray = ocorrenciasSituacaoEspecial.split(",");
        for (String propertie : propertiesArray){
            if (dePara == Integer.parseInt(propertie)){
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }
    
}
