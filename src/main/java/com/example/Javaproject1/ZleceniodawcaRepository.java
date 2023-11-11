package com.example.Javaproject1;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ZleceniodawcaRepository extends JpaRepository<Zleceniodawca, Integer> {
    Optional<Zleceniodawca> findById(int Id);

    List<Zleceniodawca>findByPelna(String pelna);
}

