package com.example.demo.repository;

import com.example.demo.model.ProfesorCatedra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorCatedraRepo extends JpaRepository<ProfesorCatedra, Long> {
}
