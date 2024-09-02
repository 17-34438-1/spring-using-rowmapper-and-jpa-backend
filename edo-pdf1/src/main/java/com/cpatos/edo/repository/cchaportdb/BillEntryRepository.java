package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.OffDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface BillEntryRepository extends JpaRepository<OffDoc, Long> {
    @Query(value ="SELECT COUNT(igm_supplimentary_detail.id) \n" +
            "FROM igm_supplimentary_detail \n" +
            "INNER JOIN igm_sup_detail_container ON igm_sup_detail_container.igm_sup_detail_id=igm_supplimentary_detail.id\n" +
            "WHERE Import_Rotation_No=:rotation AND cont_number=:container",nativeQuery = true)
    List[] getCountBillEntry(@Param("rotation") String rotation,@Param("container") String container);

    @Query(value ="SELECT igm_details.id,igm_details.Line_No,Import_Rotation_No,BL_No,cont_number,Pack_Number \n" +
            "FROM igm_details \n" +
            "INNER JOIN igm_detail_container ON igm_detail_container.igm_detail_id=igm_details.id\n" +
            "WHERE Import_Rotation_No=:rotation AND cont_number=:container\n" +
            "ORDER BY 2",nativeQuery = true)
    List[] getBillDetailEntry(@Param("rotation") String rotation,@Param("container") String container);

    @Query(value ="SELECT igm_supplimentary_detail.id,igm_supplimentary_detail.Line_No,Import_Rotation_No,BL_No,cont_number,Pack_Number \n" +
            "FROM igm_supplimentary_detail \n" +
            "INNER JOIN igm_sup_detail_container ON igm_sup_detail_container.igm_sup_detail_id=igm_supplimentary_detail.id\n" +
            "WHERE Import_Rotation_No=:rotation AND cont_number=:container\n" +
            "ORDER BY 2",nativeQuery = true)
    List[] getBillSubDetailEntry(@Param("rotation") String rotation,@Param("container") String container);



}
