package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.OrganizationLogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationLogoRepository extends JpaRepository<OrganizationLogo, Long> {

    @Query("select l.logoPath from OrganizationLogo l where l.orgId=:orgId and l.mloCode=:mloCode")
    String getLogoPathByOrgIdAndMloCode(@Param("orgId") Long orgId, @Param("mloCode") String mloCode);

}
