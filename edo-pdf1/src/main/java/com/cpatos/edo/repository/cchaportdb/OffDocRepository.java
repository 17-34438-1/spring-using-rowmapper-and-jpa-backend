package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.OffDoc;
import com.cpatos.edo.model.cchaportdb.SadItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffDocRepository extends JpaRepository<OffDoc, Long> {
    List<OffDoc> findAll();

}
