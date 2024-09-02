package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.IgmSupplimentaryDetail;
import com.cpatos.edo.model.cchaportdb.IgmSupplimentaryDetailCopy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IgmSupplimentaryDetailsCopyRepository extends JpaRepository<IgmSupplimentaryDetailCopy, Long> {



    @Modifying
    @Transactional
    @Query("UPDATE IgmSupplimentaryDetailCopy e\n" +
            "SET e.billOfEntryNo=:beNo, e.billofEntryDate=:beDate, e.officeCode=:offCode\n" +
            "WHERE e.id=:dtlID")
    int updateBeNoAndBeDateAndOffCode(@Param("dtlID") Long dtlID, @Param("beNo") String beNo, @Param("beDate") String beDate, @Param("offCode") String offCode);

}
