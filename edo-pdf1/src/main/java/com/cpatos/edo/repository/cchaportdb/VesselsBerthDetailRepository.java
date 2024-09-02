package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.VesselsBerthDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface VesselsBerthDetailRepository extends JpaRepository<VesselsBerthDetail, Long> {

    @Query("select v.etaDate from VesselsBerthDetail v where v.igmId=:igmId")
    Date getEtaDateByIgmId(@Param("igmId") Long igmId);

}
