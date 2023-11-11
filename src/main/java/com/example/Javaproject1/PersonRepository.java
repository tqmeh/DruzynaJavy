package com.example.Javaproject1;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Optional<Person> findByLogin(String login);
    List<Person> findAllByLogin(String login);
}
