package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.IgmDetails;
import com.cpatos.edo.model.cchaportdb.IgmDetailsCopy;
import jakarta.persistence.Column;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IgmDetailsCopyRepository extends JpaRepository<IgmDetailsCopy, Long> {
    @Modifying
    @Transactional


    @Query("UPDATE IgmDetailsCopy e\n" +
            "SET e.billOfEntryNo=:beNo, e.billofEntryDate=:beDate, e.officeCode=:offCode\n" +
            "WHERE e.id=:dtlID")
    int updateBeNoAndBeDateAndOffCode(@Param("dtlID") Long dtlID,@Param("beNo") String beNo, @Param("beDate") String beDate, @Param("offCode") String offCode);

}
