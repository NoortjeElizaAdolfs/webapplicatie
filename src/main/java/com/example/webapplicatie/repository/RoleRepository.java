package com.example.webapplicatie.repository;

import com.example.webapplicatie.models.ERole;
import com.example.webapplicatie.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Brug tussen JPA repository functies en role entiteit
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
