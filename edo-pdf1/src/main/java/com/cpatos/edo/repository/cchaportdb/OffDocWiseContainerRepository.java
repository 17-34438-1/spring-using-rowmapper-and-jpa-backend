package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.OffDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OffDocWiseContainerRepository extends JpaRepository<OffDoc,Long> {
    @Query(value ="SELECT * FROM (\n" +
            "SELECT cont_id,rotation,cont_status,cont_mlo,cont_size,cont_height,agent,transOp,vvd_gkey,\n" +
            "(SELECT offdoc.code FROM offdoc WHERE offdoc.id=mis_exp_unit_preadv_req.transOp) AS offDocid,\n" +
            "(SELECT offdoc.name FROM offdoc WHERE offdoc.id=mis_exp_unit_preadv_req.transOp) AS offDocName\n" +
            "FROM mis_exp_unit_preadv_req WHERE agent='APL' AND rotation=:rotation \n" +
            ") tmp  ORDER BY transOp",nativeQuery = true)
    List[] getOffDocWiseContainer(@Param("rotation") String rotation);


}
