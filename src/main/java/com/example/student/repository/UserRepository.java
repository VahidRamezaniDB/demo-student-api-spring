package com.example.student.repository;

import com.example.student.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public User getUserByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
