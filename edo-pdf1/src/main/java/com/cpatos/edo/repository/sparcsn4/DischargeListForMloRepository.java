package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DischargeListForMloRepository extends JpaRepository<ActiveMqLock,String> {

    @Query(value ="\n" +
            "select inv_unit.id,ref_bizunit_scoped.id as login_id,\n" +
            "(select substr(ref_equip_type.nominal_length,-2) from ref_equip_type \n" +
            "INNER JOIN ref_equipment ON ref_equipment.eqtyp_gkey=ref_equip_type.gkey\n" +
            "INNER JOIN inv_unit ON inv_unit.eq_gkey=ref_equipment.gkey\n" +
            "where inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            ") as cont_size,\n" +
            "(select substr(ref_equip_type.nominal_height,-2)/10 from ref_equip_type \n" +
            "INNER JOIN ref_equipment ON ref_equipment.eqtyp_gkey=ref_equip_type.gkey\n" +
            "INNER JOIN inv_unit ON inv_unit.eq_gkey=ref_equipment.gkey\n" +
            "where inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            ") as height ,\n" +
            "inv_unit.freight_kind,\n" +
            "to_char(inv_unit_fcy_visit.time_in, 'yyyy-mm-dd') as time_in,\n" +
            "to_char(inv_unit_fcy_visit.time_out,'yyyy-mm-dd') as time_out\n" +
            "from inv_unit\n" +
            "inner join inv_unit_fcy_visit on inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
            "inner join ref_bizunit_scoped on ref_bizunit_scoped.gkey= inv_unit.line_op\n" +
            "where to_char( time_out, 'yyyy-mm-dd' ) BETWEEN :from_date and :to_date ",nativeQuery = true)
    List[] getDischargeListForMlo(@Param("from_date") String from_date,@Param("to_date") String to_date);
}
