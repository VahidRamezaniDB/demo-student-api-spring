package com.example.student.repository;

import com.example.student.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User getUserByUsername(String username);
}
