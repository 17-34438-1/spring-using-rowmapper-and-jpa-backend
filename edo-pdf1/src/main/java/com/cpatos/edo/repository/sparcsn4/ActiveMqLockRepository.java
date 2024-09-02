package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.ActiveMqLock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveMqLockRepository extends JpaRepository<ActiveMqLock, Integer> {
}
