package com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrencia;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrenciaId;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbodOcorrenciaRepository extends CrudRepository<TbodOcorrencia, TbodOcorrenciaId> {
    
    @Query("SELECT MAX(tocor.tbodOcorrenciaId.nrOcorrencia) FROM TbodOcorrencia tocor INNER JOIN TbodOcorrCrm toc ON toc.tbodOcorrCrmId.idOcorrDcms = tocor.tbodOcorrenciaId.nrOcorrencia")
    Long findMaxNrOcorrencia();

    @Query(value = "select admprod.seq_nr_ocorrencia.nextval nova_ocorrencia from dual", nativeQuery = true)
    Long getNextValSequence();

    @Query("SELECT to FROM TbodOcorrencia to where to.tbodOcorrenciaId.nrOcorrencia = :nrOcorrencia")
    TbodOcorrencia findByNrOcorrencia(long nrOcorrencia);


}
