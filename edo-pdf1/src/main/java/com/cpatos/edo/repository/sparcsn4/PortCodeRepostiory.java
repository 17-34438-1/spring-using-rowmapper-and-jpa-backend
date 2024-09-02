package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortCodeRepostiory extends JpaRepository<ActiveMqLock,String> {


    @Query(value ="SELECT *\n" +
            "FROM (\n" +
            "    SELECT\n" +
            "        (SELECT id FROM ref_unloc_code WHERE gkey = tbl.unloc_gkey) id,\n" +
            "        (SELECT place_name FROM ref_unloc_code WHERE gkey = tbl.unloc_gkey) name,\n" +
            "        (SELECT cntry_code FROM ref_unloc_code WHERE gkey = tbl.unloc_gkey) country_code,\n" +
            "        (SELECT place_code FROM ref_unloc_code WHERE gkey = tbl.unloc_gkey) port_code\n" +
            "    FROM (\n" +
            "        SELECT DISTINCT unloc_gkey FROM ref_routing_point\n" +
            "    ) tbl \n" +
            ") final\n" +
            "WHERE REGEXP_LIKE(id, '[A-Za-z]')\n" +
            "ORDER BY id DESC\n" +
            "FETCH FIRST 30 ROWS ONLY",nativeQuery = true)
    List[] getPortCodeInfo();



}
