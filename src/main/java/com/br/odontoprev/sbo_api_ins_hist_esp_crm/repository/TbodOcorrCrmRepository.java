package com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrCrm;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodOcorrCrmId;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbodOcorrCrmRepository extends CrudRepository<TbodOcorrCrm, TbodOcorrCrmId> {

    @Query("SELECT to FROM TbodOcorrCrm to where to.tbodOcorrCrmId.idOcorrDcms = :nrOcorrencia")
    TbodOcorrCrm findByNrOcorrencia(long nrOcorrencia);
    
}
