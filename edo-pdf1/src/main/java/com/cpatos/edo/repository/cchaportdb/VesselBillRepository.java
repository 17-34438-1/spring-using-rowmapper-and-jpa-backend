package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.OffDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VesselBillRepository extends JpaRepository<OffDoc,Long> {
    @Query(value ="SELECT * FROM (\n" +
            "SELECT draftNumber,\n" +
            "NVL(finalNumber, draftNumber) AS finalNumber,\n" +
            "rotation,\n" +
            "vsl_name,\n" +
            "bill_name,\n" +
            "ata,\n" +
            "atd,\n" +
            "berth,\n" +
            "agent_code,\n" +
            "agent_name,\n" +
            "flag,\n" +
            "cnt_code,\n" +
            "bill_type,\n" +
            "ROW_NUMBER() OVER (ORDER BY draftNumber DESC) AS rn\n" +
            "FROM mis_vsl_billing_detail\n" +
            ")\n" +
            "WHERE rn BETWEEN 1 AND 15\n" +
            "ORDER BY draftNumber DESC",nativeQuery = true)
    List[] vesselBill();



    @Query(value ="SELECT * FROM (\n" +
            "SELECT draftNumber, \n" +
            "NVL(finalNumber, draftNumber) AS finalNumber, \n" +
            "rotation, \n" +
            "vsl_name, \n" +
            "bill_name, \n" +
            "ata, \n" +
            "atd, \n" +
            "berth, \n" +
            "agent_code, \n" +
            "agent_name, \n" +
            "flag, \n" +
            "cnt_code, \n" +
            "ROW_NUMBER() OVER (ORDER BY draftNumber DESC) AS rn\n" +
            "FROM mis_vsl_billing_detail\n" +
            "WHERE agent_code = 'GBX'\n" +
            ")WHERE rn BETWEEN 1 AND 15\n" +
            "ORDER BY draftNumber DESC;",nativeQuery = true)
    List[] vesselBillList();

}
