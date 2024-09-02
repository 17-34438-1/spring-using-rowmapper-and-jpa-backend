package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.CommudityDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommudityDetailRepository extends JpaRepository<CommudityDetail, Long> {
    List<CommudityDetail> findAll();
}
