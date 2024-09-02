package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.DoUploadWiseContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DoUploadWiseContRepository extends JpaRepository<DoUploadWiseContainer,Long> {

    @Query("select d.contIgmId from DoUploadWiseContainer d where d.shedMloDoInfoId=:uploadId")
    List<Long> getContIdList(@Param("uploadId") Long uploadId);

    @Query("select d.validUptoDate from DoUploadWiseContainer d where d.contIgmId=:contId and d.shedMloDoInfoId=:uploadId")
    Date getValidUptoDt(@Param("contId") Long contId, @Param("uploadId") Long uploadId);


    int countByShedMloDoInfoIdAndContIgmId(Long shedMloDoInfoId, Long contIgmId);

}
