package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.ShedMloDoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ShedMloDoInfoRepository extends JpaRepository<ShedMloDoInfo, Long> {
    List<ShedMloDoInfo> findAll();
    List<ShedMloDoInfo> findByEdoId(int edoId);
    ShedMloDoInfo findFirstByImpRotAndBlNoOrderByIdDesc(String impRot, String blNo);

    ShedMloDoInfo findFirstByBeNoOrderByIdDesc(String beNo);

    ShedMloDoInfo findFirstByBeNoAndBeDateOrderByIdDesc(String beNo, Date beDate);

    @Modifying
    @Transactional
    @Query("update ShedMloDoInfo s set s.cpaViewSt=1,s.cpaViewedBy=:loginId where s.id=:uploadId")
    int updateCpaViewSt(@Param("loginId") String loginId, @Param("uploadId") Long uploadId);

    @Query(value = "SELECT IFNULL(gross_quantity,0),IFNULL(SUM(delv_quantity),0) AS total_delivered,\n" +
            "(IFNULL(gross_quantity,0)-IFNULL(SUM(delv_quantity),0)) AS remaining\n" +
            "FROM shed_mlo_do_info\n" +
            "WHERE shed_mlo_do_info.imp_rot=:rotNo AND shed_mlo_do_info.bl_no=:blNo ", nativeQuery = true)
    Object totalDeliveredAndRemaining(@Param("rotNo") String rotNo, @Param("blNo") String blNo);

}
