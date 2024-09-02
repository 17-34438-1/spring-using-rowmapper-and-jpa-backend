package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.OrganizationProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationProfileRepository extends JpaRepository<OrganizationProfile, Long> {

    @Query("select o.organizationName from OrganizationProfile o where o.id=:submiteeOrgId")
    String getOrgNameById(@Param("submiteeOrgId") Long submiteeOrgId);

    @Query("select o.licenseNo from OrganizationProfile o where o.id=:orgId")
    String getLicNoById(@Param("orgId") Long orgId);

}
