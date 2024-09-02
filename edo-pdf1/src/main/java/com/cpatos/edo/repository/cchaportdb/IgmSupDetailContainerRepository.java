package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.IgmDetailContainer;
import com.cpatos.edo.model.cchaportdb.IgmSupDetailContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IgmSupDetailContainerRepository extends JpaRepository<IgmSupDetailContainer, Long> {

    List<IgmSupDetailContainer> findByIgmSupplimentaryDetailImportRotationNoAndIgmSupplimentaryDetailBlNo(String rot, String bl);

}
