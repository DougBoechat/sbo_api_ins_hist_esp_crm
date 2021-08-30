package com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodFupOcorr;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodFupOcorrId;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbodFupOcorrRepository extends CrudRepository<TbodFupOcorr, TbodFupOcorrId> {

    @Query("SELECT MAX(tfo.tbodFupOcorrId.nrSequencial) FROM TbodFupOcorr tfo WHERE tfo.tbodFupOcorrId.nrOcorrencia = :nrOcorrencia AND tfo.tbodFupOcorrId.idOcorrencia = :idOcorrencia")
    Integer findLastNrSequencial(@Param("nrOcorrencia") Long nrOcorrencia, @Param("idOcorrencia") String idOcorrencia);

    @Query("SELECT to FROM TbodFupOcorr to where to.tbodFupOcorrId.nrOcorrencia = :nrOcorrencia and to.tbodFupOcorrId.nrSequencial = 1")
    TbodFupOcorr findByNrOcorrencia(long nrOcorrencia);
    
}
