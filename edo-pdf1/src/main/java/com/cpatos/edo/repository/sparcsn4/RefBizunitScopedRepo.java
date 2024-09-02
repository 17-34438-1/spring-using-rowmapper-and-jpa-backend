package com.cpatos.edo.repository.sparcsn4;

import com.cpatos.edo.model.sparcsn4.RefBizunitScoped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefBizunitScopedRepo extends JpaRepository<RefBizunitScoped, Long> {

    RefBizunitScoped findByRoleAndLifeCycleStateAndId(String role,String lifeCycleState, String id);

}
