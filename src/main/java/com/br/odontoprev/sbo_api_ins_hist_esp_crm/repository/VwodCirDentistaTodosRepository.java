package com.br.odontoprev.sbo_api_ins_hist_esp_crm.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class VwodCirDentistaTodosRepository {

    @Autowired
    private EntityManager entityManager;

    public String findNrCgccpfByNrCgccpf(String cdCirDentista) {
        Query query = entityManager.createNativeQuery("SELECT NR_CGCCPF FROM VWOD_CIR_DENTISTA_TODOS WHERE CD_CIR_DENTISTA = :cdCirDentista");
        query.setParameter("cdCirDentista", cdCirDentista);

        return (String) query.getSingleResult();
    }
}
