package com.example.crud.repositories.auths;

import com.example.crud.models.ERole;
import com.example.crud.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
