package com.cpatos.edo.repository.cchaportdb;


import com.cpatos.edo.model.cchaportdb.EdoApplication;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EdoApplicationRepository extends JpaRepository<EdoApplication, Long> {

    Integer countByRotationAndBl(String rot, String bl);

    List<EdoApplication> findByRotationAndBl(String rot, String bl);

    Integer countByRotationAndBlAndRejectionSt(String rot, String bl, Integer rejectionSt);

    @Modifying
    @Transactional
    @Query("update EdoApplication e set e.cnfVldtyApprSt=1,e.appliedValidDt=:vDate,e.appliedValidityExtensionAt=CURRENT_TIMESTAMP where e.id=:edoId")
    int updateCnfValidityApprovalSt(@Param("edoId") Long edoId, @Param("vDate") Date vDate);

    @Query("select e.igmType from EdoApplication e where e.rotation=:rot and e.bl=:bl")
    String getEdoIgmType(@Param("rot") String rot, @Param("bl") String bl);

    @Query("select e.entryTime from EdoApplication e where e.rotation=:rot and e.bl=:bl")
    LocalDateTime getEntryTime(@Param("rot") String rot, @Param("bl") String bl);

    @Query("select e.ffClearanceTime from EdoApplication e where e.rotation=:rot and e.bl=:bl")
    Date getFfClearanceTime(@Param("rot") String rot, @Param("bl") String bl);

    @Query("select e.ffOrgId from EdoApplication e where e.rotation=:rot and e.bl=:bl")
    String getFfOrgid(@Param("rot") String rot, @Param("bl") String bl);

    @Query("select e.mlo from EdoApplication e where e.rotation=:rot and e.bl=:bl")
    String getMloOrgid(@Param("rot") String rot, @Param("bl") String bl);

    @Query("select e.shAgentOrgId from EdoApplication e where e.rotation=:rot and e.bl=:bl")
    String getShippingAgentOrgid(@Param("rot") String rot, @Param("bl") String bl);

    List<EdoApplication> findTop50ByBlTypeAndIgmTypeAndFfStatOrderByIdDesc(String blType, String igmType, int ffStat);

    List<EdoApplication> findTop50ByIgmTypeAndShAgentOrgIdOrderByIdDesc(String igmType, String shAgentOrgId);


    List<EdoApplication> findTop50ByIgmTypeAndFfOrgIdAndDoUploadStAndVldtyApprByMloStOrderByIdDesc(
            String igmType, String ffOrgId, int doUploadSt, int vldtyApprByMloSt);

    List<EdoApplication> findTop100ByIgmTypeAndMloAndDoUploadStAndCnfVldtyApprStOrderByIdDesc(String igmType, String mlo, int doUploadSt, int cnfVldtyApprSt);

    List<EdoApplication> findTop100ByIgmTypeOrderByIdDesc(String igmType);

    List<EdoApplication> findTop50BySubmittedByOrderByIdDesc(String submittedBy);

    List<EdoApplication> findByBlTypeAndIgmTypeAndFfAssocStOrDoUploadSt(String blType, String igmType, Integer ffAssocSt, Integer doUploadSt);

    List<EdoApplication> findTop50ByBlAndSubmittedByOrderByIdDesc(String bl,String submittedBy);

    List<EdoApplication> findTop50ByRotationAndSubmittedByOrderByIdDesc(String rotation,String submittedBy);




    @Modifying
    @Transactional
    @Query("UPDATE EdoApplication e SET e.rejectionSt = 1, e.rejectionTime = CURRENT_TIMESTAMP, e.rejectionRemarks = :rejectionRemarks, e.rejectedByOrg = :orgId, e.rejectedByUser = :loginId WHERE e.id = :eid")
    void updateRejectionStByEid(@Param("eid") Long eid, @Param("rejectionRemarks") String rejectionRemarks, @Param("orgId") String orgId, @Param("loginId") String loginId);


    @Modifying
    @Transactional
    @Query("UPDATE EdoApplication e SET e.rejectionSt=0, e.rejectionWithdrawnTime= CURRENT_TIMESTAMP, e.rejectionWithdrawnRemarks= :rejectionRemarks,e.withdrawnByOrg = :orgId, withdrawnByUser=:loginId WHERE e.id = :eid")
    void updateWithdrawStByEid(@Param("eid")long eid, @Param("rejectionRemarks") String rejectionRemarks, @Param("orgId") String orgId, @Param("loginId") String loginId);

    Optional<EdoApplication> findById(Long id);


    @Modifying
    @Transactional
    @Query("UPDATE EdoApplication e SET e.ffStat = 1, e.ffClearanceTime = CURRENT_TIMESTAMP, e.forwardedBy = :loginId, e.forwardedOrgId = :mloId WHERE e.id = :eid")
    int updateFFStat(@Param("loginId") String loginId, @Param("mloId") String mloId,@Param("eid") Long eid);


    @Modifying
    @Transactional
    @Query("UPDATE EdoApplication e SET e.ffStat = 1, e.ffClearanceTime = CURRENT_TIMESTAMP, e.forwardedBy = :loginId, e.forwardedOrgId = :mloId WHERE e.mblOfHbl=:mblOfHbl and e.ffStat=0")
    void clearPendingApplications(@Param("loginId") String loginId,@Param("mloId") String mloId,@Param("mblOfHbl") String mblOfHbl);


    @Modifying
    @Transactional
    @Query("UPDATE EdoApplication e SET e.ffStat= 1, e.ffClearanceTime = CURRENT_TIMESTAMP,e.forwardedBy = :loginId,e.forwardedOrgId = :mloId,e.validUptoDtByMlo=:validUptoDate,\n" +
            "e.appliedValidDt=:validUptoDate\n" +
            "WHERE e.id=:edoId")
    int updateFFStatAndAppliedValidDt(@Param("edoId") Long edoId,@Param("validUptoDate") Date validUptoDate, @Param("mloId") String mloId, @Param("loginId") String loginId);



    @Modifying
    @Transactional
    @Query("UPDATE EdoApplication e SET e.ffStat = 1, e.ffClearanceTime = CURRENT_TIMESTAMP, e.forwardedBy = :loginId, e.forwardedOrgId = :mloId,e.validUptoDtByMlo=:validUptoDate,\n" +
            "e.appliedValidDt=:validUptoDate WHERE e.mblOfHbl=:mblOfHbl and e.ffStat=0")
    int clearPendingApplicationsAndAppliedValidDt(@Param("validUptoDate") Date validUptoDate, @Param("mblOfHbl") String mblOfHbl, @Param("mloId") String mloId, @Param("loginId") String loginId);

    @Query(value = "SELECT edo_application_by_cf.ff_org_id, " +
            "organization_profiles.Organization_Name, " +
            "organization_profiles.Address_1, " +
            "organization_profiles.Address_2, " +
            "organization_profiles.License_No, " +
            "organization_profiles.AIN_No_New, " +
            "organization_profiles.logo, " +
            "organization_profiles.Cell_No_1, " +
            "organization_profiles.Telephone_No_Land " +
            "FROM edo_application_by_cf " +
            "INNER JOIN organization_profiles ON edo_application_by_cf.ff_org_id = organization_profiles.id " +
            "WHERE edo_application_by_cf.id = :edoId", nativeQuery = true)
    List<Object[]> findOrganizationDetailsByFfOrgId(@Param("edoId") Long edoId);



    @Query(value = "SELECT edo_application_by_cf.mlo, " +
            "organization_profiles.Organization_Name, " +
            "organization_profiles.Address_1, " +
            "organization_profiles.Address_2, " +
            "organization_profiles.License_No, " +
            "organization_profiles.AIN_No_New, " +
            "organization_profiles.logo, " +
            "organization_profiles.Cell_No_1, " +
            "organization_profiles.Telephone_No_Land " +
            "FROM edo_application_by_cf " +
            "INNER JOIN organization_profiles ON edo_application_by_cf.mlo = organization_profiles.id " +
            "WHERE edo_application_by_cf.id = :edoId", nativeQuery = true)
    List<Object[]> findOrganizationDetailsByMlo(@Param("edoId") Long edoId);


    @Query(value = "SELECT edo_application_by_cf.sh_agent_org_id, " +
            "organization_profiles.Organization_Name, " +
            "organization_profiles.Address_1, " +
            "organization_profiles.Address_2, " +
            "organization_profiles.License_No, " +
            "organization_profiles.AIN_No_New, " +
            "organization_profiles.logo, " +
            "organization_profiles.Cell_No_1, " +
            "organization_profiles.Telephone_No_Land " +
            "FROM edo_application_by_cf " +
            "INNER JOIN organization_profiles ON edo_application_by_cf.sh_agent_org_id = organization_profiles.id " +
            "WHERE edo_application_by_cf.id = :edoId", nativeQuery = true)
    List<Object[]> findOrganizationDetailsByShAgentOrgId(@Param("edoId") Long edoId);




}
