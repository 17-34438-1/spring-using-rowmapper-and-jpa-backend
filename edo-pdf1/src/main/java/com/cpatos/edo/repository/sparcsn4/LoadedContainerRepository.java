package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LoadedContainerRepository extends JpaRepository<ActiveMqLock,String> {

    @Query(value ="SELECT inv_unit.gkey,inv_unit.id AS cont_id,vsl_vessel_visit_details.ib_vyg,g.id AS mlo,Y.id AS agent,SUBSTR(ref_equip_type.nominal_length,-2, LENGTH( ref_equip_type.nominal_length)) AS siz, SUBSTR(ref_equip_type.nominal_height, -2, LENGTH( ref_equip_type.nominal_height)) AS height,\n" +
            "ref_equip_type.id AS iso,inv_unit.freight_kind,inv_unit.goods_and_ctr_wt_kg,REF_ROUTING_POINT.ID as pod, inv_unit_fcy_visit.LAST_POS_LOCTYPE AS coming_frm,inv_unit.seal_nbr1 AS sealno,\n" +
            "vsl_vessel_visit_details.ib_vyg,vsl_vessels.name,\n" +
            "(select ref_bizunit_scoped.id from ref_bizunit_scoped\n" +
            "inner join road_trucks on road_trucks.trkco_gkey=ref_bizunit_scoped.gkey\n" +
            "inner join ROAD_TRUCK_VISIT_DETAILS on ROAD_TRUCK_VISIT_DETAILS.truck_gkey=road_trucks.gkey\n" +
            "inner join road_truck_transactions on road_truck_transactions.truck_visit_gkey=road_truck_visit_details.tvdtls_gkey\n" +
            "where road_truck_transactions.unit_gkey=inv_unit.gkey fetch first 1 rows only) AS coming_from,\n" +
            "(select srv_event.placed_time from srv_event where srv_event.applied_to_gkey=inv_unit.gkey and event_type_gkey IN(31488) \n" +
            "ORDER BY srv_event.gkey DESC fetch first 1 rows only) as pre_advised_dt,inv_unit_fcy_visit.last_pos_slot AS stowage_pos,inv_unit.remark,ref_commodity.SHORT_NAME\n" +
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
            "LEFT JOIN inv_goods ON inv_goods.gkey=inv_unit.goods\n" +
            "LEFT JOIN ref_commodity ON ref_commodity.gkey=inv_goods.commodity_gkey\n" +
            "WHERE  vsl_vessel_visit_details.ib_vyg=:rotation and TO_CHAR(INV_UNIT_FCY_VISIT.TIME_OUT, 'YYYY-MM-DD') \n" +
            "BETWEEN :from_date AND :to_date ",nativeQuery = true)
    List[] LoadedContainer(@Param("rotation") String rotation, @Param("from_date") String from_date, @Param("to_date") String to_date);



    @Query(value ="SELECT \n" +
            "NVL(SUM(onboard_LD_20),0) AS onboard_LD_20,\n" +
            "NVL(SUM(onboard_LD_40),0) AS onboard_LD_40,\n" +
            "NVL(SUM(onboard_MT_20),0) AS onboard_MT_20,\n" +
            "NVL(SUM(onboard_MT_40),0) AS onboard_MT_40,\n" +
            "NVL(SUM(onboard_LD_tues),0) AS onboard_LD_tues,\n" +
            "NVL(SUM(onboard_MT_tues),0) AS onboard_MT_tues\n" +
            "\n" +
            "FROM (\n" +
            "SELECT DISTINCT inv_unit.gkey AS gkey,vsl_vessel_visit_details.ib_vyg,category,\n" +
            "(CASE WHEN substr(nominal_length,-2) = '20' AND freight_kind IN ('FCL','LCL')  THEN 1  \n" +
            "ELSE NULL END) AS onboard_LD_20, \n" +
            "(CASE WHEN substr(nominal_length,-2) > '20' AND freight_kind IN ('FCL','LCL')  THEN 1  \n" +
            "ELSE NULL END) AS onboard_LD_40,\n" +
            "(CASE WHEN substr(nominal_length,-2) = '20' AND freight_kind ='MTY'  THEN 1  \n" +
            "ELSE NULL END) AS onboard_MT_20, \n" +
            "(CASE WHEN substr(nominal_length,-2) > '20' AND freight_kind ='MTY'  THEN 1  \n" +
            "ELSE NULL END) AS onboard_MT_40, \n" +
            "(CASE WHEN substr(nominal_length,-2)='20' AND freight_kind IN ('FCL','LCL') THEN 1 \n" +
            "ELSE (CASE WHEN substr(nominal_length,2)>'20' AND freight_kind IN ('FCL','LCL') THEN 2 ELSE NULL END) END) AS onboard_LD_tues, \n" +
            "(CASE WHEN substr(nominal_length,-2)=20 AND freight_kind='MTY' THEN 1 \n" +
            "ELSE (CASE WHEN substr(nominal_length,-5)>'20' AND freight_kind='MTY' THEN 2 ELSE NULL END) END) AS onboard_MT_tues\n" +
            "\n" +
            "FROM inv_unit\n" +
            "INNER JOIN inv_unit_fcy_visit ON inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            "INNER JOIN argo_carrier_visit ON argo_carrier_visit.gkey=inv_unit_fcy_visit.actual_ib_cv\n" +
            "INNER JOIN vsl_vessel_visit_details ON vsl_vessel_visit_details.vvd_gkey=argo_carrier_visit.cvcvd_gkey\n" +
            "INNER JOIN ref_equipment ON ref_equipment.gkey=inv_unit.eq_gkey\n" +
            "INNER JOIN ref_equip_type ON ref_equip_type.gkey=ref_equipment.eqtyp_gkey\n" +
            "WHERE category='EXPRT'and vsl_vessel_visit_details.ib_vyg=:rotation AND inv_unit_fcy_visit.transit_state ='S20_INBOUND'\n" +
            ")",nativeQuery = true)
    List[] getDischargeReport(@Param("rotation") String rotation);



    @Query(value ="SELECT\n" +
            "NVL(SUM(balance_LD_20),0) AS balance_LD_20,\n" +
            "NVL(SUM(balance_LD_40),0) AS balance_LD_40,\n" +
            "NVL(SUM(balance_MT_20),0) AS balance_MT_20,\n" +
            "NVL(SUM(balance_MT_40),0) AS balance_MT_40,\n" +
            "NVL(SUM(balance_LD_tues),0) AS balance_LD_tues,\n" +
            "NVL(SUM(balance_MT_tues),0) AS balance_MT_tues\n" +
            "\n" +
            "FROM (\n" +
            "SELECT vsl_vessel_visit_details.vvd_gkey,vsl_vessel_visit_details.ib_vyg,category,\n" +
            "(CASE WHEN substr(nominal_length,-2) = '20' AND freight_kind IN ('FCL','LCL')  THEN 1  \n" +
            "ELSE NULL END) AS balance_LD_20, \n" +
            "(CASE WHEN substr(nominal_length,-2) > '20' AND freight_kind IN ('FCL','LCL')  THEN 1  \n" +
            "ELSE NULL END) AS balance_LD_40,\n" +
            "(CASE WHEN substr(nominal_length,-2) = '20' AND freight_kind ='MTY'  THEN 1  \n" +
            "ELSE NULL END) AS balance_MT_20, \n" +
            "(CASE WHEN substr(nominal_length,-2) > '20' AND freight_kind ='MTY'  THEN 1  \n" +
            "ELSE NULL END) AS balance_MT_40, \n" +
            "(CASE WHEN substr(nominal_length,-2) = '20' AND freight_kind IN ('FCL','LCL') THEN 1 \n" +
            "ELSE (CASE WHEN substr(nominal_length,-2) > '20' AND freight_kind IN ('FCL','LCL') THEN 2 ELSE NULL END) END) AS balance_LD_tues, \n" +
            "(CASE WHEN substr(nominal_length,-2) = '20' AND freight_kind='MTY' THEN 1 \n" +
            "ELSE (CASE WHEN substr(nominal_length,-2) > '20' AND freight_kind='MTY' THEN 2 ELSE NULL END) END) AS balance_MT_tues\n" +
            "\n" +
            "FROM  inv_unit \n" +
            "INNER JOIN inv_unit_fcy_visit ON inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            "INNER JOIN argo_carrier_visit ON argo_carrier_visit.gkey=inv_unit_fcy_visit.actual_ib_cv\n" +
            "INNER JOIN vsl_vessel_visit_details ON vsl_vessel_visit_details.vvd_gkey=argo_carrier_visit.cvcvd_gkey\n" +
            "LEFT JOIN inv_goods ON inv_goods.gkey=inv_unit.goods\n" +
            "LEFT JOIN ref_commodity ON ref_commodity.gkey=inv_goods.commodity_gkey\n" +
            "INNER JOIN ref_equipment ON ref_equipment.gkey=inv_unit.eq_gkey\n" +
            "INNER JOIN ref_equip_type ON ref_equip_type.gkey=ref_equipment.eqtyp_gkey\n" +
            "INNER JOIN ref_bizunit_scoped ON ref_bizunit_scoped.gkey=inv_unit.line_op\n" +
            "WHERE  category='EXPRT' and  vsl_vessel_visit_details.ib_vyg=:rotation and transit_state not in ('S60_LOADED','S70_DEPARTED','S99_RETIRED')\n" +
            ")  tmp",nativeQuery = true)
    List[] getBalanceReport(@Param("rotation") String rotation);

}
