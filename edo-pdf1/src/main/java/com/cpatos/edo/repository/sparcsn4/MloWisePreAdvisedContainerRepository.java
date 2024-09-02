package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MloWisePreAdvisedContainerRepository extends JpaRepository<ActiveMqLock,String> {
    @Query(value ="SELECT mlo,agent,\n" +
            "NVL(SUM(Loaded_20),0) AS Loaded_20,\n" +
            "NVL(SUM(Loaded_40),0) AS Loaded_40,\n" +
            "NVL(SUM(EMTY_20),0) AS EMTY_20,\n" +
            "NVL(SUM(EMTY_40),0) AS EMTY_40,\n" +
            "NVL(SUM(REEFER_20),0) AS REEFER_20,\n" +
            "NVL(SUM(REEFER_40),0) AS REEFER_40,\n" +
            "NVL(SUM(IMDG_20),0) AS IMDG_20,\n" +
            "NVL(SUM(IMDG_40),0) AS IMDG_40,\n" +
            "NVL(SUM(TRSHP_20),0) AS TRSHP_20,\n" +
            "NVL(SUM(TRSHP_40),0) AS TRSHP_40,\n" +
            "NVL(SUM(ICD_20),0) AS ICD_20,\n" +
            "NVL(SUM(ICD_40),0) AS ICD_40,\n" +
            "NVL(SUM(LD_20),0) AS LD_20,\n" +
            "NVL(SUM(LD_40),0) AS LD_40,\n" +
            "NVL(SUM(grand_tot),0) AS grand_tot,\n" +
            "NVL(SUM(tues),0) AS tues\n" +
            "FROM (\n" +
            "SELECT DISTINCT inv_unit.gkey AS gkey,g.id AS mlo,Y.id AS agent,vsl_vessel_visit_details.ib_vyg,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) =20  AND inv_unit.freight_kind IN ('FCL','LCL') and ref_equip_type.id not in('22R1','45R1','45R0','25R1','45R3','22R0','42R1','45R8','20R1','22R9','42R0','22R2','20R0','45R4','22R7','42R3','45R5') THEN 1  \n" +
            "ELSE NULL END) AS Loaded_20, \n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) !=20  AND inv_unit.freight_kind IN ('FCL','LCL') and ref_equip_type.id not in('22R1','45R1','45R0','25R1','45R3','22R0','42R1','45R8','20R1','22R9','42R0','22R2','20R0','45R4','22R7','42R3','45R5')  THEN 1  \n" +
            "ELSE NULL END) AS Loaded_40, \n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) = 20 AND inv_unit.freight_kind ='MTY'  THEN 1  \n" +
            "ELSE NULL END) AS EMTY_20, \n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) != 20 AND inv_unit.freight_kind ='MTY'  THEN 1  \n" +
            "ELSE NULL END) AS EMTY_40, \n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) = 20 AND inv_unit.freight_kind IN ('FCL','LCL') AND ref_equip_type.id in('22R1','45R1','45R0','25R1','45R3','22R0','42R1','45R8','20R1','22R9','42R0','22R2','20R0','45R4','22R7','42R3','45R5')  THEN 1  \n" +
            "ELSE NULL END) AS REEFER_20, \n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) != 20 AND inv_unit.freight_kind IN ('FCL','LCL') AND ref_equip_type.id in('22R1','45R1','45R0','25R1','45R3','22R0','42R1','45R8','20R1','22R9','42R0','22R2','20R0','45R4','22R7','42R3','45R5')  THEN 1  \n" +
            "ELSE NULL END) AS REEFER_40,\n" +
            "'' AS IMDG_20, \n" +
            "'' AS IMDG_40, \n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) =20  AND inv_unit.freight_kind IN ('FCL','LCL','MTY') AND inv_unit.category='TRSHP' THEN 1  \n" +
            "ELSE NULL END) AS TRSHP_20, \n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) !=20  AND inv_unit.freight_kind IN ('FCL','LCL','MTY') AND inv_unit.category='TRSHP' THEN 1  \n" +
            "ELSE NULL END) AS TRSHP_40, \n" +
            "'' AS ICD_20, \n" +
            "'' AS ICD_40, \n" +
            "'' AS LD_20, \n" +
            "'' AS LD_40, \n" +
            "1 AS grand_tot,\n" +
            " (CASE WHEN substr(ref_equip_type.nominal_length,-2) =20  THEN 1 ELSE 2 END) AS tues \n" +
            "FROM  inv_unit\n" +
            "inner join inv_unit_fcy_visit on inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            "INNER JOIN ( ref_bizunit_scoped g LEFT JOIN ( ref_agent_representation X LEFT JOIN ref_bizunit_scoped Y ON X.agent_gkey=Y.gkey ) ON g.gkey=X.bzu_gkey ) ON g.gkey = inv_unit.line_op\n" +
            "inner join argo_carrier_visit on argo_carrier_visit.gkey=inv_unit_fcy_visit.actual_ob_cv\n" +
            "inner join vsl_vessel_visit_details on vsl_vessel_visit_details.vvd_gkey=argo_carrier_visit.cvcvd_gkey\n" +
            "INNER JOIN ref_equipment ON ref_equipment.gkey=inv_unit.eq_gkey\n" +
            "INNER JOIN ref_equip_type ON ref_equip_type.gkey=ref_equipment.eqtyp_gkey\n" +
            "WHERE  vsl_vessel_visit_details.ib_vyg=:rotation\n" +
            ") tmp GROUP BY mlo,agent ",nativeQuery = true)
    List[] getMloWisePreAdvisedContainer(@Param("rotation") String rotation);


    @Query(value ="SELECT inv_unit.gkey,inv_unit.id AS cont_id,vsl_vessel_visit_details.ib_vyg,g.id AS mlo,Y.id AS agent,SUBSTR(ref_equip_type.nominal_length,-2, LENGTH( ref_equip_type.nominal_length)) AS cont_size, SUBSTR(ref_equip_type.nominal_height, -2, LENGTH( ref_equip_type.nominal_height)) AS height,\n" +
            "ref_equip_type.id AS iso,inv_unit.freight_kind,inv_unit.goods_and_ctr_wt_kg,REF_ROUTING_POINT.ID as pod, inv_unit_fcy_visit.LAST_POS_LOCTYPE AS coming_frm,inv_unit.seal_nbr1 AS sealno,\n" +
            "vsl_vessel_visit_details.ib_vyg,vsl_vessels.name,\n" +
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
            "WHERE  vsl_vessel_visit_details.ib_vyg=:rotation AND g.id=:mlo",nativeQuery = true)
    List[] getPreAdvisedContainer(@Param("rotation") String rotation,@Param("mlo") String mlo);


    @Query(value ="SELECT mlo,agent,\n" +
            "NVL(SUM(F_20),0) AS F_20,\n" +
            "NVL(SUM(L_20),0) AS L_20,\n" +
            "NVL(SUM(M_20),0) AS M_20,\n" +
            "NVL(SUM(I_20),0) AS I_20,\n" +
            "NVL(SUM(T_20),0) AS T_20,\n" +
            "NVL(SUM(R_20),0) AS R_20,\n" +
            "NVL(SUM(IMDG_20),0) AS IMDG_20, \n" +
            "NVL(SUM(F_40),0) AS F_40,\n" +
            "NVL(SUM(L_40),0) AS L_40,\n" +
            "NVL(SUM(M_40),0) AS M_40,\n" +
            "NVL(SUM(I_40),0) AS I_40,\n" +
            "NVL(SUM(T_40),0) AS T_40,\n" +
            "NVL(SUM(R_40),0) AS R_40,\n" +
            "NVL(SUM(IMDG_40),0) AS IMDG_40,\n" +
            "\n" +
            "NVL(SUM(FW_20),0) AS FW_20,\n" +
            "NVL(SUM(LW_20),0) AS LW_20,\n" +
            "NVL(SUM(MW_20),0) AS MW_20,\n" +
            "NVL(SUM(IW_20),0) AS IW_20,\n" +
            "NVL(SUM(TW_20),0) AS TW_20,\n" +
            "NVL(SUM(RW_20),0) AS RW_20,\n" +
            "NVL(SUM(IMDGW_20),0) AS IMDGW_20, \n" +
            "NVL(SUM(FW_40),0) AS FW_40,\n" +
            "NVL(SUM(LW_40),0) AS LW_40,\n" +
            "NVL(SUM(MW_40),0) AS MW_40,\n" +
            "NVL(SUM(IW_40),0) AS IW_40,\n" +
            "NVL(SUM(TW_40),0) AS TW_40,\n" +
            "NVL(SUM(RW_40),0) AS RW_40,\n" +
            "NVL(SUM(IMDGW_40),0) AS IMDGW_40\n" +
            "FROM (\n" +
            "\n" +
            "SELECT vsl_vessel_visit_details.ib_vyg,inv_unit.gkey AS gkey,g.id AS mlo,Y.id AS agent,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) = '20' AND inv_unit.freight_kind IN ('FCL') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN 1  \n" +
            "ELSE NULL END) AS F_20,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) = '20' AND inv_unit.freight_kind IN ('LCL') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN 1  \n" +
            "ELSE NULL END) AS L_20,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) = '20' AND inv_unit.freight_kind IN ('MTY') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN 1  \n" +
            "ELSE NULL END) AS M_20,\n" +
            "0 AS I_20,\n" +
            "0 AS T_20,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) = '20' AND ref_equip_type.id IN ('RS','RT','RE')  THEN 1  \n" +
            "ELSE NULL END) AS R_20,\n" +
            "0 AS IMDG_20,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) IN ('40','45') AND inv_unit.freight_kind IN ('FCL') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN 1  \n" +
            "ELSE NULL END) AS F_40,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) IN ('40','45') AND inv_unit.freight_kind IN ('LCL') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN 1  \n" +
            "ELSE NULL END) AS L_40,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) IN ('40','45') AND inv_unit.freight_kind IN ('MTY') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN 1  \n" +
            "ELSE NULL END) AS M_40,\n" +
            "0 AS I_40,\n" +
            "0 AS T_40,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) IN ('40','45') AND ref_equip_type.id IN ('RS','RT','RE')  THEN 1  \n" +
            "ELSE NULL END) AS R_40,\n" +
            "0 AS IMDG_40,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) = '20' AND inv_unit.freight_kind IN ('FCL') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN goods_and_ctr_wt_kg  \n" +
            "ELSE NULL END) AS FW_20,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) = '20' AND inv_unit.freight_kind IN ('LCL') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN goods_and_ctr_wt_kg  \n" +
            "ELSE NULL END) AS LW_20,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) = '20' AND inv_unit.freight_kind IN ('MTY') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN goods_and_ctr_wt_kg \n" +
            "ELSE NULL END) AS MW_20,\n" +
            "0 AS IW_20,\n" +
            "0 AS TW_20,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) = '20' AND ref_equip_type.id IN ('RS','RT','RE')  THEN goods_and_ctr_wt_kg  \n" +
            "ELSE NULL END) AS RW_20,\n" +
            "0 AS IMDGW_20,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) IN ('40','45') AND inv_unit.freight_kind IN ('FCL') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN goods_and_ctr_wt_kg  \n" +
            "ELSE NULL END) AS FW_40,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) IN ('40','45') AND inv_unit.freight_kind IN ('LCL') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN goods_and_ctr_wt_kg  \n" +
            "ELSE NULL END) AS LW_40,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) IN ('40','45') AND inv_unit.freight_kind IN ('MTY') AND ref_equip_type.id NOT IN ('RS','RT','RE')  THEN goods_and_ctr_wt_kg  \n" +
            "ELSE NULL END) AS MW_40,\n" +
            "0 AS IW_40,\n" +
            "0 AS TW_40,\n" +
            "(CASE WHEN substr(ref_equip_type.nominal_length,-2) IN ('40','45') AND ref_equip_type.id IN ('RS','RT','RE')  THEN goods_and_ctr_wt_kg  \n" +
            "ELSE NULL END) AS RW_40,\n" +
            "0 AS IMDGW_40\n" +
            "\n" +
            "FROM  inv_unit\n" +
            "inner join inv_unit_fcy_visit on inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            "INNER JOIN ( ref_bizunit_scoped g LEFT JOIN ( ref_agent_representation X LEFT JOIN ref_bizunit_scoped Y ON X.agent_gkey=Y.gkey ) ON g.gkey=X.bzu_gkey ) ON g.gkey = inv_unit.line_op\n" +
            "inner join argo_carrier_visit on argo_carrier_visit.gkey=inv_unit_fcy_visit.actual_ob_cv\n" +
            "inner join vsl_vessel_visit_details on vsl_vessel_visit_details.vvd_gkey=argo_carrier_visit.cvcvd_gkey\n" +
            "INNER JOIN ref_equipment ON ref_equipment.gkey=inv_unit.eq_gkey\n" +
            "INNER JOIN ref_equip_type ON ref_equip_type.gkey=ref_equipment.eqtyp_gkey\n" +
            "WHERE  vsl_vessel_visit_details.ib_vyg=:rotation AND g.id=:mlo\n" +
            ")  tmp GROUP BY mlo,agent",nativeQuery = true)
    List[] getMloWiseSummary(@Param("rotation") String rotation,@Param("mlo") String mlo);


}
