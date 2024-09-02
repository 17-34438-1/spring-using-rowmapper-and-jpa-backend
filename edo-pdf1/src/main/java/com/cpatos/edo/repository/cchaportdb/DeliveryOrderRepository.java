package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.OffDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryOrderRepository extends JpaRepository<OffDoc,Long> {
    @Query(value ="SELECT BL_NO,Pack_Number,Pack_Marks_Number,weight,weight_unit,Pack_Description,Description_of_Goods,cont_number,cont_size,cont_status,Bill_of_Entry_No FROM igm_supplimentary_detail supDtl \n" +
            "INNER JOIN igm_sup_detail_container supDtlCont ON supDtl.id=supDtlCont.igm_sup_detail_id\n" +
            "INNER JOIN igm_masters im ON supDtl.igm_master_id= im.id\n" +
            "WHERE supDtl.Bill_of_Entry_No=:bill_of_vessel",nativeQuery = true)
    List[] getVesselBill(@Param("bill_of_vessel") String bill_of_vessel);

    @Query(value ="select BL_NO,Notify_name,Line_No,bill_of_entry_date,supDtl.Import_Rotation_No,Vessel_Name,Voy_No \n" +
            "from igm_supplimentary_detail supDtl \n" +
            "inner join igm_sup_detail_container supDtlCont on supDtl.id=supDtlCont.igm_sup_detail_id\n" +
            "inner join igm_masters im on supDtl.igm_master_id= im.id\n" +
            "where supDtl.Bill_of_Entry_No=:bill_of_vessel\n",nativeQuery = true)
    List[] getDeliveryOrderVesselInfo(@Param("bill_of_vessel") String bill_of_vessel);


}
