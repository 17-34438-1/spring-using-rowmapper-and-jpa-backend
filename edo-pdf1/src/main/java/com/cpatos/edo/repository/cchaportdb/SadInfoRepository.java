package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.SadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SadInfoRepository extends JpaRepository<SadInfo, Long> {

    List<SadInfo> findBySadItemsSumDeclare(String sumDeclare);

    List<SadInfo> findBySadItemsSumDeclareAndManifNumContaining(String sumDeclare, String manifNum);


    List<SadInfo> findBySadItemsSumDeclareAndManifNumContainingOrManifNumContaining(String sumDeclare, String manifNum, String manifNum2);

    Long countByRegNoAndRegDate(String beNo, Date beDate);

}
