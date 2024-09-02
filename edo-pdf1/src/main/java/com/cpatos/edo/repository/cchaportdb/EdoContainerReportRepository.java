package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.OffDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EdoContainerReportRepository extends JpaRepository<OffDoc, Long> {

    @Query(value ="SELECT shed_mlo_do_info.imp_rot,shed_mlo_do_info.bl_no, igm_detail_container.cont_number,\n" +
            "igm_detail_container.cont_size,igm_detail_container.cont_height,\n" +
            "igm_detail_container.cont_iso_type,igm_masters.Vessel_Name,to_char(shed_mlo_do_info.upload_time) AS upload_date,\n" +
            "shed_mlo_do_info.remarks, shed_mlo_do_info.valid_upto_dt,\n" +
            "shed_mlo_do_info.upload_time,\n" +
            "edo_application_by_cf.sumitted_by,users.u_name AS CF,\n" +
            "edo_application_by_cf.mlo\n" +
            "FROM shed_mlo_do_info \n" +
            "INNER JOIN edo_application_by_cf ON shed_mlo_do_info.edo_id=edo_application_by_cf.id\n" +
            "INNER JOIN users ON edo_application_by_cf.sumitted_by=users.login_id\n" +
            "INNER JOIN do_upload_wise_container ON do_upload_wise_container.shed_mlo_do_info_id=shed_mlo_do_info.id\n" +
            "INNER JOIN igm_detail_container ON igm_detail_container.id= do_upload_wise_container.cont_igm_id\n" +
            "INNER JOIN igm_details ON igm_details.id=igm_detail_container.igm_detail_id \n" +
            "INNER JOIN igm_masters ON  igm_masters.id=igm_details.IGM_id\n" +
            "WHERE edo_application_by_cf.do_upload_st = '1' \n" +
            "AND (TRUNC(shed_mlo_do_info.upload_time) BETWEEN TO_DATE(:from_date, 'YYYY-MM-DD') AND TO_DATE(:to_date, 'YYYY-MM-DD'))\n" +
            "ORDER BY bl_no,cont_number FETCH FIRST 100 ROWS ONLY",nativeQuery = true)
    List[] getEdoContainerInfo(@Param("from_date") String from_date,@Param("to_date") String to_date);

    @Query(value ="SELECT shed_mlo_do_info.imp_rot,shed_mlo_do_info.bl_no, \n" +
            "igm_sup_detail_container.cont_number,igm_sup_detail_container.cont_size,\n" +
            "igm_sup_detail_container.cont_height,\n" +
            "igm_sup_detail_container.cont_iso_type,  igm_masters.Vessel_Name, to_char(shed_mlo_do_info.upload_time)  AS upload_date,\n" +
            "shed_mlo_do_info.remarks,shed_mlo_do_info.valid_upto_dt, \n" +
            "shed_mlo_do_info.upload_time,\n" +
            "edo_application_by_cf.sumitted_by,users.u_name AS CF,\n" +
            "edo_application_by_cf.mlo\n" +
            "FROM shed_mlo_do_info \n" +
            "INNER JOIN edo_application_by_cf ON shed_mlo_do_info.edo_id=edo_application_by_cf.id\n" +
            "INNER JOIN users ON edo_application_by_cf.sumitted_by=users.login_id\n" +
            "INNER JOIN do_upload_wise_container ON do_upload_wise_container.shed_mlo_do_info_id=shed_mlo_do_info.id\n" +
            "INNER JOIN igm_sup_detail_container ON igm_sup_detail_container.id= do_upload_wise_container.cont_igm_id\n" +
            "INNER JOIN igm_supplimentary_detail ON igm_supplimentary_detail.id=igm_sup_detail_container.igm_sup_detail_id \n" +
            "INNER JOIN igm_masters ON  igm_masters.id=igm_supplimentary_detail.igm_master_id\n" +
            "WHERE edo_application_by_cf.do_upload_st = '1' \n" +
            "AND (TRUNC(shed_mlo_do_info.upload_time) BETWEEN TO_DATE(:from_date, 'YYYY-MM-DD') AND TO_DATE(:to_date, 'YYYY-MM-DD'))\n" +
            "ORDER BY bl_no,cont_number FETCH FIRST 100 ROWS ONLY",nativeQuery = true )
    List[] getEdoContainer(@Param("from_date") String from_date,@Param("to_date") String to_date);



    @Query(value ="SELECT shed_mlo_do_info.imp_rot,shed_mlo_do_info.bl_no, igm_detail_container.cont_number, igm_detail_container.cont_size,\n" +
            "igm_detail_container.cont_height,\n" +
            "igm_detail_container.cont_iso_type,  igm_masters.Vessel_Name, to_char(shed_mlo_do_info.upload_time)  AS upload_date,\n" +
            "shed_mlo_do_info.remarks, shed_mlo_do_info.valid_upto_dt,\n" +
            "shed_mlo_do_info.upload_time,\n" +
            "edo_application_by_cf.sumitted_by,users.u_name AS CF,\n" +
            "edo_application_by_cf.mlo\n" +
            "FROM shed_mlo_do_info \n" +
            "INNER JOIN edo_application_by_cf ON shed_mlo_do_info.edo_id=edo_application_by_cf.id\n" +
            "INNER JOIN users ON edo_application_by_cf.sumitted_by=users.login_id\n" +
            "INNER JOIN do_upload_wise_container ON do_upload_wise_container.shed_mlo_do_info_id=shed_mlo_do_info.id\n" +
            "INNER JOIN igm_detail_container ON igm_detail_container.id= do_upload_wise_container.cont_igm_id\n" +
            "INNER JOIN igm_details ON igm_details.id=igm_detail_container.igm_detail_id \n" +
            "INNER JOIN igm_masters ON  igm_masters.id=igm_details.IGM_id\n" +
            "WHERE edo_application_by_cf.do_upload_st = '1' \n" +
            "AND (TRUNC(shed_mlo_do_info.upload_time) BETWEEN TO_DATE('2023-04-12', 'YYYY-MM-DD') AND TO_DATE('2023-10-12', 'YYYY-MM-DD'))\n" +
            "UNION ALL\n" +
            "SELECT shed_mlo_do_info.imp_rot,shed_mlo_do_info.bl_no, igm_sup_detail_container.cont_number, igm_sup_detail_container.cont_size,\n" +
            "igm_sup_detail_container.cont_height,\n" +
            "igm_sup_detail_container.cont_iso_type,  igm_masters.Vessel_Name, to_char(shed_mlo_do_info.upload_time)  AS upload_date,\n" +
            "shed_mlo_do_info.remarks,shed_mlo_do_info.valid_upto_dt, \n" +
            "shed_mlo_do_info.upload_time,\n" +
            "edo_application_by_cf.sumitted_by,users.u_name AS CF,\n" +
            "edo_application_by_cf.mlo\n" +
            "FROM shed_mlo_do_info \n" +
            "INNER JOIN edo_application_by_cf ON shed_mlo_do_info.edo_id=edo_application_by_cf.id\n" +
            "INNER JOIN users ON edo_application_by_cf.sumitted_by=users.login_id\n" +
            "INNER JOIN do_upload_wise_container ON do_upload_wise_container.shed_mlo_do_info_id=shed_mlo_do_info.id\n" +
            "INNER JOIN igm_sup_detail_container ON igm_sup_detail_container.id= do_upload_wise_container.cont_igm_id\n" +
            "INNER JOIN igm_supplimentary_detail ON igm_supplimentary_detail.id=igm_sup_detail_container.igm_sup_detail_id \n" +
            "INNER JOIN igm_masters ON  igm_masters.id=igm_supplimentary_detail.igm_master_id\n" +
            "WHERE edo_application_by_cf.do_upload_st = '1' \n" +
            "AND (TRUNC(shed_mlo_do_info.upload_time) BETWEEN TO_DATE(:from_date, 'YYYY-MM-DD') AND TO_DATE(:to_date, 'YYYY-MM-DD'))\n" +
            "ORDER BY bl_no,cont_number FETCH FIRST 100 ROWS ONLY",nativeQuery = true)
    List[] getEdoContainerReport(@Param("from_date") String from_date,@Param("to_date") String to_date);
}
