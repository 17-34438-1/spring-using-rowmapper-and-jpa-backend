package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.ShedMloDoInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AllAssignmentReportRepo extends JpaRepository<ShedMloDoInfoModel, Long> {
    @Query(value ="SELECT  tmp.*,\n" +
            "(CASE WHEN time_out IS NULL THEN '2' ELSE '1' END) s1 \n" +
            "FROM ( \n" +
            "SELECT cont_no,rot_no,iso_code,mlo,cont_status,mfdch_desc,weight,assignmentdate,time_out,slot,ship_id,time_in,\n" +
            "CASE\n" +
            "  WHEN stay IS NULL THEN ''\n" +
            "  ELSE stay\n" +
            "END AS stay FROM tmp_oracle_assignment \n" +
            "WHERE TRUNC(tmp_oracle_assignment.assignmentDate) = TO_DATE(:from_date, 'YYYY-MM-DD') AND  tmp_oracle_assignment.mfdch_value NOT IN ('CANCEL','OCD','APPCUS','APPOTH','APPREF') \n" +
            "ORDER BY tmp_oracle_assignment.assignmentDate DESC       \n" +
            ")\n" +
            "tmp",nativeQuery = true)
    List[] getAllAssignment(@Param("from_date") String from_date);
}
