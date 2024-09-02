package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.IgmSupplimentaryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IgmSupplimentaryDetailsRepository extends JpaRepository<IgmSupplimentaryDetail, Long> {
    Integer countByImportRotationNoAndBlNo(String rot, String bl);
    List<IgmSupplimentaryDetail> findByImportRotationNoAndBlNo(String rot, String bl);

    Integer countByBlNo(String bl);
    List<IgmSupplimentaryDetail> findByBlNo(String bl);
}
