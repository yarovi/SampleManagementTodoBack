package org.yasmani.io.todomanagerapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasmani.io.todomanagerapp.dto.LoginDto;
import org.yasmani.io.todomanagerapp.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    //Optional findByRole(String role);

    Role findByRoleName(String role);



}
