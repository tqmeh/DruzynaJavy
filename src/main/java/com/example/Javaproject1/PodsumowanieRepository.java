package com.example.Javaproject1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PodsumowanieRepository extends JpaRepository<Podsumowanie,Integer> {
    List<Podsumowanie> findAllByNumer(int numer);
    @SuppressWarnings("unused")
    Optional<Podsumowanie> findByNumer(int numer);
}
