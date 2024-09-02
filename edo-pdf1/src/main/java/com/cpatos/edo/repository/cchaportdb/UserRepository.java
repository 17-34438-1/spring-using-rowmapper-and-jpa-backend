package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByLoginId(String loginId);
}
