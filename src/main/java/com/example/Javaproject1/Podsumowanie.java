package com.example.Javaproject1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// do pobierania tabeli podsumowanie
@Entity
public class Podsumowanie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private int numer;
    private String Dataa;
    private String Wykonane;
    private double Kwota;

    public Podsumowanie()
    {

    }
    @SuppressWarnings("unused")
    public Podsumowanie(int id, int numer, String dataa, String wykonane, double kwota) {
        Id = id;
        this.numer = numer;
        Dataa = dataa;
        Wykonane = wykonane;
        Kwota = kwota;
    }
    @SuppressWarnings("unused")
    public int getId() {
        return Id;
    }
    @SuppressWarnings("unused")
    public void setId(int id) {
        Id = id;
    }
    @SuppressWarnings("unused")
    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public String getDataa() {
        return Dataa;
    }

    public void setDataa(String dataa) {
        Dataa = dataa;
    }

    public String getWykonane() {
        return Wykonane;
    }


    public double getKwota() {
        return Kwota;
    }

    public void setKwota(double kwota) {
        Kwota = kwota;
    }

    public void setWykonane(String wykonane) {
        Wykonane = wykonane;
    }

    @Override
    public String toString() {
        return "Podsumowanie{" +
                "Id=" + Id +
                ", numer=" + numer +
                ", Dataa='" + Dataa + '\'' +
                ", Wykonane='" + Wykonane + '\'' +
                ", Kwota=" + Kwota +
                '}';
    }
}
