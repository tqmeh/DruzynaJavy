package com.example.Javaproject1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
public class Numer {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private int Numer;


    public Numer()
    {

    }
    @SuppressWarnings("unused")
    public Numer(int id, int numer) {
        Id = id;
        Numer = numer;
    }
    @SuppressWarnings("unused")
    public int getId() {
        return Id;
    }
    @SuppressWarnings("unused")
    public void setId(int id) {
        Id = id;
    }

    public int getNumer() {
        return Numer;
    }

    public void setNumer(int numer) {
        Numer = numer;
    }
}
