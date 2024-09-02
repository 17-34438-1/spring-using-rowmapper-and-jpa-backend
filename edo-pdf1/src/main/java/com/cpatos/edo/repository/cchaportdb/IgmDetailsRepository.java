package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.IgmDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IgmDetailsRepository extends JpaRepository<IgmDetails, Long> {
    Integer countByImportRotationNoAndBlNo(String rot, String bl);

    List<IgmDetails> findByImportRotationNoAndBlNo(String rot, String bl);

    @Query("select i.billOfEntryNo from IgmDetails i where i.importRotationNo=:rot and i.blNo=:bl")
    String getBeNo(@Param("rot") String rot, @Param("bl") String bl);

    List<IgmDetails> findByBlNo(String bl);

    List<IgmDetails> findByImportRotationNo(String rot);
}
