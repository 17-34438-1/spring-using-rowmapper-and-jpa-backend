package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.IgmMasters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IgmMastersRepository extends JpaRepository<IgmMasters, Long> {
    @Query(value = " SELECT DISTINCT igm_details.id AS dtl_id,BL_No,Pack_Number,Pack_Description,Pack_Marks_Number,weight,Bill_of_Entry_No,\n" +
            "    igm_details.Bill_of_Entry_Date,igm_details.office_code,\n" +
            "    No_of_Pack_Delivered,DG_status,type_of_igm,net_weight,weight_unit,net_weight_unit,igm_details.Consignee_name,Consignee_address,\n" +
            "    Description_of_Goods,igm_details.Volume_in_cubic_meters,\n" +
            "    igm_masters.id,igm_masters.Import_Rotation_No,vessels_berth_detail.ETA_Date,igm_masters.Vessel_Name,igm_masters.Voy_No,\n" +
            "    igm_masters.Net_Tonnage,Notify_name,Notify_address,port_of_origin,Port_of_Shipment,igm_details.Pack_Marks_Number,\n" +
            "    igm_masters.Name_of_Master,igm_masters.Port_Ship_ID Port_of_Shipment,igm_masters.Port_of_Destination,igm_masters.custom_approved,\n" +
            "    igm_masters.file_clearence_date,Organization_Name AS org_name,igm_masters.Submitee_Org_Type AS Submitee_Org_Type,\n" +
            "    igm_masters.S_Org_License_Number AS S_Org_License_Number,igm_masters.Submission_Date AS Submission_Date,igm_masters.flag AS flag,\n" +
            "    igm_masters.imo AS imo, reg_no,dec_code,igm_details.Exporter_name,igm_details.Exporter_address\n" +
            "    FROM igm_masters\n" +
            "    INNER JOIN igm_details ON  igm_masters.id=igm_details.IGM_id\n" +
            "    LEFT JOIN sad_item ON sad_item.sum_declare=igm_details.BL_No\n" +
            "    LEFT JOIN sad_info ON sad_info.id=sad_item.sad_id\n" +
            "    LEFT JOIN vessels_berth_detail ON vessels_berth_detail.igm_id = igm_masters.id\n" +
            "    LEFT JOIN organization_profiles ON organization_profiles.id = igm_masters.Submitee_Org_Id\n" +
            "    WHERE igm_details.Import_Rotation_No=:rotNo AND BL_No=:blNo ORDER BY file_clearence_date DESC", nativeQuery = true)
    List<Object[]> findIgmDetailsByRotNoAndBlNO(@Param("rotNo") String rotNo, @Param("blNo") String blNo);



    @Query(value = "SELECT DISTINCT igm_supplimentary_detail.id AS dtl_id,igm_supplimentary_detail.BL_No,igm_supplimentary_detail.Pack_Number,igm_supplimentary_detail.Pack_Description,igm_supplimentary_detail.Pack_Marks_Number,igm_supplimentary_detail.weight,igm_supplimentary_detail.Bill_of_Entry_No,\n" +
            "igm_supplimentary_detail.Bill_of_Entry_Date,igm_supplimentary_detail.office_code,\n" +
            "igm_supplimentary_detail.No_of_Pack_Delivered,igm_supplimentary_detail.DG_status,igm_supplimentary_detail.type_of_igm,igm_supplimentary_detail.net_weight,igm_supplimentary_detail.weight_unit,igm_supplimentary_detail.net_weight_unit,igm_supplimentary_detail.Consignee_name,igm_supplimentary_detail.Consignee_address,\n" +
            "igm_supplimentary_detail.Description_of_Goods,igm_supplimentary_detail.Volume_in_cubic_meters,\n" +
            "igm_masters.id,igm_masters.Import_Rotation_No,vessels_berth_detail.ETA_Date,igm_masters.Vessel_Name,igm_masters.Voy_No,\n" +
            "igm_masters.Net_Tonnage,igm_supplimentary_detail.Notify_name,igm_supplimentary_detail.Notify_address,igm_supplimentary_detail.port_of_origin,Port_of_Shipment,igm_details.Pack_Marks_Number,\n" +
            "igm_masters.Name_of_Master,igm_masters.Port_Ship_ID Port_of_Shipment,igm_masters.Port_of_Destination,igm_masters.custom_approved,\n" +
            "igm_masters.file_clearence_date,Organization_Name AS org_name,igm_masters.Submitee_Org_Type AS Submitee_Org_Type,\n" +
            "igm_masters.S_Org_License_Number AS S_Org_License_Number,igm_masters.Submission_Date AS Submission_Date,igm_masters.flag AS flag,\n" +
            "igm_masters.imo AS imo,reg_no,dec_code,igm_details.Exporter_name,igm_details.Exporter_address\n" +
            "FROM igm_masters\n" +
            "INNER JOIN igm_details ON  igm_masters.id=igm_details.IGM_id\n" +
            "INNER JOIN igm_supplimentary_detail ON igm_supplimentary_detail.igm_detail_id=igm_details.id\n" +
            "LEFT JOIN sad_item ON sad_item.sum_declare=igm_details.BL_No\n" +
            "LEFT JOIN sad_info ON sad_info.id=sad_item.sad_id\n" +
            "LEFT JOIN vessels_berth_detail ON vessels_berth_detail.igm_id = igm_masters.id\n" +
            "LEFT JOIN organization_profiles ON organization_profiles.id = igm_masters.Submitee_Org_Id\n" +
            "WHERE igm_supplimentary_detail.Import_Rotation_No=:rotNo AND igm_supplimentary_detail.BL_No=:blNo ORDER BY file_clearence_date DESC", nativeQuery = true)
    List<Object[]> findIgmSupplimentaryDetailByRotNoAndBlNO(@Param("rotNo") String rotNo, @Param("blNo") String blNo);

}
