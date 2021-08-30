package com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository;

import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodHistSitEspecial;
import com.br.odontoprev.sbo_api_ins_hist_esp_crm.model.TbodHistSitEspecialId;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbodHistSitEspecialRepository extends CrudRepository<TbodHistSitEspecial, TbodHistSitEspecialId> {

    @Query("SELECT to FROM TbodHistSitEspecial to where to.tbodHistSitEspecialId.nrOcorrencia = :nrOcorrencia")
    TbodHistSitEspecial findByNrOcorrencia(long nrOcorrencia);

}
