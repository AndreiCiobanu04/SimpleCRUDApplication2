package com.example.demo.repository;

import com.example.demo.model.Catedra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatedraRepo extends JpaRepository<Catedra, Long> {
}
