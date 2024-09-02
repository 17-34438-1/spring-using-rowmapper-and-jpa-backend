package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface VesselTransitWaysInfoRepository extends JpaRepository<ActiveMqLock,String>
{

    @Query(value ="select vsl,ib_vyg,ata,etd,berth,ship_agent,berth_op,phase,\n" +
            "(SELECT LISTAGG(id, '->') AS transit_way FROM ref_point_calls \n" +
            "INNER JOIN ref_routing_point ON ref_point_calls.point_gkey=ref_routing_point.gkey\n" +
            "WHERE itin_gkey=(SELECT itinereray FROM argo_visit_details WHERE gkey=vvd_gkey)) AS transit_way\n" +
            "from (\n" +
            "SELECT NVL(vsl_vessel_visit_details.vvd_gkey,'') AS vvd_gkey,\n" +
            "vsl_vessels.name AS vsl,\n" +
            "NVL(vsl_vessel_visit_details.ib_vyg,'') AS ib_vyg,\n" +
            "NVL(argo_carrier_visit.ata,'') AS ata, \n" +
            "NVL(argo_visit_details.etd,'') AS etd,\n" +
            "NVL((SELECT argo_quay.id FROM argo_quay \n" +
            "INNER JOIN vsl_vessel_berthings ON vsl_vessel_berthings.quay=argo_quay.gkey \n" +
            "WHERE vsl_vessel_berthings.vvd_gkey=vsl_vessel_visit_details.vvd_gkey ORDER BY vsl_vessel_berthings.gkey FETCH FIRST 1 ROW ONLY),'') AS berth,\n" +
            "NVL(Y.name,'') AS ship_agent,\n" +
            "NVL(vsl_vessel_visit_details.flex_string02,\n" +
            "NVL(vsl_vessel_visit_details.flex_string03,'')) AS berth_op,\n" +
            "NVL(argo_carrier_visit.phase,'') AS PHASE FROM vsl_vessel_visit_details \n" +
            "INNER JOIN argo_carrier_visit ON argo_carrier_visit.cvcvd_gkey=vsl_vessel_visit_details.vvd_gkey \n" +
            "INNER JOIN vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey \n" +
            "INNER JOIN argo_visit_details ON argo_visit_details.gkey=vsl_vessel_visit_details.vvd_gkey \n" +
            "INNER JOIN ref_carrier_service ON ref_carrier_service.gkey=argo_visit_details.service \n" +
            "INNER JOIN ( ref_bizunit_scoped r LEFT JOIN ( ref_agent_representation X \n" +
            "LEFT JOIN ref_bizunit_scoped Y ON X.agent_gkey=Y.gkey ) ON r.gkey=X.bzu_gkey) ON r.gkey = vsl_vessel_visit_details.bizu_gkey\n" +
            "WHERE vsl_vessel_visit_details.ib_vyg=:rotation\n" +
            ") a",nativeQuery = true)
    List[] getVesselTransitWaysInfo(@Param("rotation") String rotation);

}
