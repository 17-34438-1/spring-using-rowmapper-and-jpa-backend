package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.IgmDetailContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IgmDtlContRepo extends JpaRepository<IgmDetailContainer, Long> {
    List<IgmDetailContainer> findByIgmDetailsImportRotationNoAndIgmDetailsBlNo(String rot, String bl);
}
