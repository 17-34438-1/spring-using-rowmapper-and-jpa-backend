package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExportContainerGateInRepository extends JpaRepository<ActiveMqLock,String> {


    @Query(value = "SELECT count(inv.id) chkNum \n" +
            "FROM inv_unit inv  \n" +
            "INNER JOIN inv_unit_fcy_visit ON inv_unit_fcy_visit.unit_gkey=inv.gkey\n" +
            "INNER JOIN argo_carrier_visit ON argo_carrier_visit.gkey=inv_unit_fcy_visit.actual_ob_cv\n" +
            "INNER JOIN vsl_vessel_visit_details ON vsl_vessel_visit_details.vvd_gkey=argo_carrier_visit.cvcvd_gkey\n" +
            "INNER JOIN ref_bizunit_scoped g ON vsl_vessel_visit_details.bizu_gkey = g.gkey\n" +
            "where vsl_vessel_visit_details.ob_vyg=:rotation \n" +
            "order by inv_unit_fcy_visit.time_in desc",nativeQuery = true)
    List[] chkListQuery(@Param("rotation") String rotation);


@Query(value = "SELECT inv_unit.id,substr(ref_equip_type.nominal_length,-2) cont_size,\n" +
        "substr(ref_equip_type.nominal_height,-2)/10 AS height,inv_unit.seal_nbr1,vsl_vessels.name,\n" +
        "g.id AS MLO,inv_unit.category,inv_unit.freight_kind,\n" +
        "vsl_vessel_visit_details.ob_vyg AS rotation,vsl_vessel_visit_details.ob_vyg,\n" +
        "inv_unit_fcy_visit.time_in\n" +
        "FROM inv_unit\n" +
        "LEFT JOIN inv_unit_fcy_visit ON inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
        "LEFT JOIN argo_carrier_visit ON argo_carrier_visit.gkey=inv_unit_fcy_visit.actual_ob_cv\n" +
        "LEFT JOIN vsl_vessel_visit_details ON vsl_vessel_visit_details.vvd_gkey=argo_carrier_visit.cvcvd_gkey\n" +
        "LEFT JOIN vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey\n" +
        "LEFT JOIN ref_bizunit_scoped g ON inv_unit.line_op = g.gkey\n" +
        "LEFT JOIN ref_equipment ON inv_unit.eq_gkey=ref_equipment.gkey\n" +
        "LEFT JOIN ref_equip_type ON ref_equipment.eqtyp_gkey=ref_equip_type.gkey \n" +
        "WHERE vsl_vessel_visit_details.ob_vyg=:rotation and \n" +
        "inv_unit_fcy_visit.time_in is not null  \n" +
        "ORDER BY inv_unit.gkey DESC FETCH FIRST 100 ROWS ONLY",nativeQuery = true)
    List[] getExportContainerGateIn(@Param("rotation") String rotation);


}
