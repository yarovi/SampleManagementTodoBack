package org.yasmani.io.todomanagerapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasmani.io.todomanagerapp.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);
    //Boolean existsEmail(String email);

    //Optional<User> findByEmail(String name,String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
