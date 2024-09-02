package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VesselAndTransitRepository extends JpaRepository<ActiveMqLock,String>
{

    @Query(value ="SELECT vsl_vessels.name FROM vsl_vessel_visit_details\n" +
            "INNER JOIN vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey\n" +
            "WHERE vsl_vessel_visit_details.vvd_gkey=:VVD_GKEY",nativeQuery = true)
    List[] getVesselList(@Param("VVD_GKEY") String VVD_GKEY);

    @Query(value ="select inv_unit_fcy_visit.transit_state,inv_unit.category \n" +
            "from inv_unit \n" +
            "inner join inv_unit_fcy_visit on inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            "where inv_unit.id=:cont_id order by inv_unit_fcy_visit.gkey",nativeQuery = true)
    List[] getTransitInfo(@Param("cont_id") String cont_id);


}
