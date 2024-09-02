package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgentWiseVessel extends JpaRepository<ActiveMqLock,String> {

    @Query(value ="SELECT vsl_vessels.name, argo_carrier_visit.phase, ata, atd, vsl_vessel_visit_details.ib_vyg, vvd_gkey\n" +
            "FROM vsl_vessel_visit_details \n" +
            "INNER JOIN vsl_vessels ON vsl_vessels.gkey = vsl_vessel_visit_details.vessel_gkey\n" +
            "INNER JOIN argo_carrier_visit ON vsl_vessel_visit_details.vvd_gkey = argo_carrier_visit.cvcvd_gkey\n" +
            "ORDER BY vvd_gkey DESC\n" +
            "FETCH FIRST 50 ROWS ONLY",nativeQuery = true)
    List[] getAgentWiseVessel();

    @Query(value ="SELECT inv_unit.id AS cont_id,vsl_vessels.name as VesselName, vsl_vessel_visit_details.ib_vyg as rotation,SUBSTR(ref_equip_type.nominal_length,-2, LENGTH( ref_equip_type.nominal_length)) AS cont_size,SUBSTR(ref_equip_type.nominal_height, -2, LENGTH( ref_equip_type.nominal_height)) AS cont_height,g.id AS cont_mlo,inv_unit.freight_kind as cont_status, \n" +
            "Y.id AS agent,inv_unit_fcy_visit.transit_state, inv_unit.category,inv_unit.goods_and_ctr_wt_kg,REF_ROUTING_POINT.ID as pod, inv_unit_fcy_visit.LAST_POS_LOCTYPE AS coming_frm,inv_unit.seal_nbr1 AS sealno,\n" +
            "vsl_vessel_visit_details.ib_vyg,\n" +
            "(select ref_bizunit_scoped.id from ref_bizunit_scoped\n" +
            "inner join road_trucks on road_trucks.trkco_gkey=ref_bizunit_scoped.gkey\n" +
            "inner join ROAD_TRUCK_VISIT_DETAILS on ROAD_TRUCK_VISIT_DETAILS.truck_gkey=road_trucks.gkey\n" +
            "inner join road_truck_transactions on road_truck_transactions.truck_visit_gkey=road_truck_visit_details.tvdtls_gkey\n" +
            "where road_truck_transactions.unit_gkey=inv_unit.gkey fetch first 1 rows only) AS coming_from,\n" +
            "(select srv_event.placed_time from srv_event where srv_event.applied_to_gkey=inv_unit.gkey and event_type_gkey IN(31488) \n" +
            "ORDER BY srv_event.gkey DESC fetch first 1 rows only) as pre_advised_dt \n" +
            "FROM  inv_unit\n" +
            "inner join inv_unit_fcy_visit on inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            "INNER JOIN ( ref_bizunit_scoped g LEFT JOIN ( ref_agent_representation X LEFT JOIN ref_bizunit_scoped Y ON X.agent_gkey=Y.gkey ) ON g.gkey=X.bzu_gkey ) ON g.gkey = inv_unit.line_op\n" +
            "inner join argo_carrier_visit on argo_carrier_visit.gkey=inv_unit_fcy_visit.actual_ob_cv\n" +
            "inner join vsl_vessel_visit_details on vsl_vessel_visit_details.vvd_gkey=argo_carrier_visit.cvcvd_gkey\n" +
            "INNER JOIN vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey \n" +
            "INNER JOIN ref_equipment ON ref_equipment.gkey=inv_unit.eq_gkey\n" +
            "INNER JOIN ref_equip_type ON ref_equip_type.gkey=ref_equipment.eqtyp_gkey\n" +
            "INNER JOIN ref_bizunit_scoped  ON inv_unit.line_op = ref_bizunit_scoped.gkey \n" +
            "INNER JOIN REF_ROUTING_POINT ON INV_UNIT.POD1_GKEY = REF_ROUTING_POINT.GKEY\n" +
            "WHERE  vsl_vessel_visit_details.ib_vyg=:rotation FETCH FIRST 20 ROWS ONLY",nativeQuery = true)
    List[] getAgentWiseVessel(@Param("rotation") String rotation);
}
