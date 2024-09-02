package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.ClearedMblByMlo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClearedMblByMloRepository extends JpaRepository<ClearedMblByMlo, Long> {

    Integer countByMasterBl(String masterBl);
    List<ClearedMblByMlo> findByMasterBl(String masterBl);
}
