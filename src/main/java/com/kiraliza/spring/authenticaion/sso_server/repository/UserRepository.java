package com.kiraliza.spring.authenticaion.sso_server.repository;

import com.kiraliza.spring.authenticaion.sso_server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>
{
    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
