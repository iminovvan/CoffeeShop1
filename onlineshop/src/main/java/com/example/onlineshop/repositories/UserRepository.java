package com.example.onlineshop.repositories;

import com.example.onlineshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //@Query(value = "select * from users where email = ?", nativeQuery = true)
    Optional<User> findByEmail(String email);
}
