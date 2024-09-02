package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.EdoAppliedValidityDate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface EdoAppliedValidityDateRepository extends JpaRepository<EdoAppliedValidityDate,Long> {

    @Modifying
    @Transactional
    int deleteByEdoId(Long edoId);

    int countByEdoIdAndContIgmId(Long edoId,Long contIgmId);
}
