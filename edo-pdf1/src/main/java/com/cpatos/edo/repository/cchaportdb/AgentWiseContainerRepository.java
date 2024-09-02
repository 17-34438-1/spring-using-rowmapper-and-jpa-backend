package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.OffDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgentWiseContainerRepository extends JpaRepository<OffDoc,Long> {
    @Query(value ="SELECT * FROM (\n" +
            "SELECT cont_id,rotation,cont_status,cont_mlo,cont_size,cont_height,agent,transOp,vvd_gkey\n" +
            "FROM mis_exp_unit_preadv_req WHERE agent='APL' and  rotation=:rotation \n" +
            ") tmp  ORDER BY cont_id DESC ",nativeQuery = true)
    List[] getAgentContainerByRotation(@Param("rotation") String rotation);

    @Query(value ="SELECT * FROM (\n" +
            "SELECT cont_id,rotation,cont_status,cont_mlo,cont_size,cont_height,agent,transOp,vvd_gkey\n" +
            "FROM mis_exp_unit_preadv_req WHERE agent='APL' and  cont_id=:cont_id \n" +
            ") tmp  ORDER BY cont_id DESC FETCH FIRST 50 ROWS ONLY",nativeQuery = true)
    List[] getAgentContainerByContid(@Param("cont_id") String cont_id);


    @Query(value ="SELECT * FROM (\n" +
            "SELECT cont_id,rotation,cont_status,cont_mlo,cont_size,cont_height,agent,transOp,vvd_gkey\n" +
            "FROM mis_exp_unit_preadv_req WHERE agent='APL' and  cont_mlo=:cont_mlo \n" +
            ") tmp  ORDER BY cont_id DESC FETCH FIRST 50 ROWS ONLY",nativeQuery = true)
    List[] getAgentContainerByContMlo(@Param("cont_mlo") String cont_mlo);



    @Query(value ="SELECT * FROM (\n" +
            "SELECT cont_id,rotation,cont_status,cont_mlo,cont_size,cont_height,agent,transOp,vvd_gkey\n" +
            "FROM mis_exp_unit_preadv_req WHERE agent='APL' and  pod=:pod \n" +
            ") tmp  ORDER BY cont_id DESC FETCH FIRST 50 ROWS ONLY",nativeQuery = true)
    List[] getAgentContainerByPod(@Param("pod") String pod);

    @Query(value ="SELECT * FROM (\n" +
            "SELECT cont_id,rotation,cont_status,cont_mlo,cont_size,cont_height,agent,transOp,vvd_gkey\n" +
            "FROM mis_exp_unit_preadv_req WHERE agent='APL' and  transOp=:transOp \n" +
            ") tmp  ORDER BY cont_id DESC FETCH FIRST 50 ROWS ONLY",nativeQuery = true)
    List[] getAgentContainerByTransOp(@Param("transOp") String transOp);

}
