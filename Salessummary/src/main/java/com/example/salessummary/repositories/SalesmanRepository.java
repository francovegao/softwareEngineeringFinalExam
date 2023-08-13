package com.example.salessummary.repositories;

import com.example.salessummary.entities.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesmanRepository extends JpaRepository<Salesman,Long> {
}
