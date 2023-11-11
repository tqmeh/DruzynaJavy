package com.example.Javaproject1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZlecenieRepository extends JpaRepository<Zlecenie,Integer> {

    Optional<Zlecenie> findByNumer(int numer);
}
