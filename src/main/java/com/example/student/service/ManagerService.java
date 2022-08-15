package com.example.student.service;

import com.example.student.repository.ManagerRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    private ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }
}
