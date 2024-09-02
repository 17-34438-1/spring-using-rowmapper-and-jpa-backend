package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImportDischargeAndBalanceReportRepository extends JpaRepository<ActiveMqLock,String> {


    @Query(value ="SELECT CONCAT(CONCAT(substr(inv_unit.id,1,4),' '),substr(inv_unit.id,5)) AS id,inv_unit_fcy_visit.time_in AS fcy_time_in,\n" +
            "inv_unit_fcy_visit.last_pos_slot AS location,inv_unit.seal_nbr1 AS sealno,\n" +
            "ref_equip_type.id AS iso,ref_bizunit_scoped.id AS mlo,inv_unit.freight_kind,inv_unit.goods_and_ctr_wt_kg AS weight,\n" +
            "ref_commodity.short_name,inv_unit.remark,\n" +
            "(SELECT time_discharge_complete\n" +
            "FROM vsl_vessel_visit_details\n" +
            "INNER JOIN argo_visit_details ON argo_visit_details.gkey = vsl_vessel_visit_details.vvd_gkey\n" +
            "WHERE ib_vyg=inv_unit_fcy_visit.flex_string10 FETCH FIRST 1 ROWS ONLY) AS cl_date\n" +
            "FROM  inv_unit \n" +
            "INNER JOIN inv_unit_fcy_visit ON inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            "INNER JOIN argo_carrier_visit ON argo_carrier_visit.gkey=inv_unit_fcy_visit.actual_ib_cv\n" +
            "INNER JOIN vsl_vessel_visit_details ON vsl_vessel_visit_details.vvd_gkey=argo_carrier_visit.cvcvd_gkey\n" +
            "LEFT JOIN inv_goods ON inv_goods.gkey=inv_unit.goods\n" +
            "LEFT JOIN ref_commodity ON ref_commodity.gkey=inv_goods.commodity_gkey\n" +
            "INNER JOIN ref_equipment ON ref_equipment.gkey=inv_unit.eq_gkey\n" +
            "INNER JOIN ref_equip_type ON ref_equip_type.gkey=ref_equipment.eqtyp_gkey\n" +
            "INNER JOIN ref_bizunit_scoped ON ref_bizunit_scoped.gkey=inv_unit.line_op\n" +
            "WHERE vsl_vessel_visit_details.ib_vyg=:rotation AND category='IMPRT' AND inv_unit_fcy_visit.transit_state='S20_INBOUND'",nativeQuery = true)
    List[] getImportReportBalance(@Param("rotation") String rotation);

    @Query(value ="SELECT CONCAT(CONCAT(substr(inv_unit.id,1,4),' '),substr(inv_unit.id,5)) AS id,inv_unit_fcy_visit.time_in AS fcy_time_in,\n" +
            "inv_unit_fcy_visit.last_pos_slot AS location,inv_unit.seal_nbr1 AS sealno,\n" +
            "ref_equip_type.id AS iso,ref_bizunit_scoped.id AS mlo,inv_unit.freight_kind,inv_unit.goods_and_ctr_wt_kg AS weight,\n" +
            "ref_commodity.short_name,inv_unit.remark,\n" +
            "(SELECT time_discharge_complete\n" +
            "FROM vsl_vessel_visit_details\n" +
            "INNER JOIN argo_visit_details ON argo_visit_details.gkey = vsl_vessel_visit_details.vvd_gkey\n" +
            "WHERE ib_vyg=inv_unit_fcy_visit.flex_string10 FETCH FIRST 1 ROWS ONLY) AS cl_date\n" +
            "FROM  inv_unit \n" +
            "INNER JOIN inv_unit_fcy_visit ON inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            "INNER JOIN argo_carrier_visit ON argo_carrier_visit.gkey=inv_unit_fcy_visit.actual_ib_cv\n" +
            "INNER JOIN vsl_vessel_visit_details ON vsl_vessel_visit_details.vvd_gkey=argo_carrier_visit.cvcvd_gkey\n" +
            "LEFT JOIN inv_goods ON inv_goods.gkey=inv_unit.goods\n" +
            "LEFT JOIN ref_commodity ON ref_commodity.gkey=inv_goods.commodity_gkey\n" +
            "INNER JOIN ref_equipment ON ref_equipment.gkey=inv_unit.eq_gkey\n" +
            "INNER JOIN ref_equip_type ON ref_equip_type.gkey=ref_equipment.eqtyp_gkey\n" +
            "INNER JOIN ref_bizunit_scoped ON ref_bizunit_scoped.gkey=inv_unit.line_op\n" +
            "WHERE vsl_vessel_visit_details.ib_vyg=:rotation AND category='IMPRT' AND inv_unit_fcy_visit.time_in IS NOT NULL",nativeQuery = true)
    List[] getImportReportDischarge(@Param("rotation") String rotation);


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
            "WHERE category='IMPRT'and vsl_vessel_visit_details.ib_vyg=:rotation AND inv_unit_fcy_visit.transit_state NOT IN ('S20_INBOUND','S30_ECIN') \n" +
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
            "WHERE  category='IMPRT' and  vsl_vessel_visit_details.ib_vyg=:rotation AND inv_unit_fcy_visit.transit_state ='S20_INBOUND'\n" +
            ")  tmp",nativeQuery = true)
    List[] getBalanceReport(@Param("rotation") String rotation);

}
