package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.EdoApplicationDeleteLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EdoAppDltLogRepository extends JpaRepository<EdoApplicationDeleteLog, Long> {
}
