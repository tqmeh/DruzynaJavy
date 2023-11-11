package com.example.Javaproject1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumerRepository extends JpaRepository<Numer, Integer> {
}
