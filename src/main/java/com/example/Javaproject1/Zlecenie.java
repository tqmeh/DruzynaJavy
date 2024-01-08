package com.example.Javaproject1;

import jakarta.persistence.*;

@Entity
public class Zlecenie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private int numer;
    private String Zleceniodawca;
    private String Przyjecie;
    private  String Urzadzenie;
    private String Marka;
    private String Model;
    private String Seryjny;
    private String Usterka;
    private String Wady;
    private String Uwagi;
    private String Zakonczenie;
    private String Status;

    public Zlecenie()
    {

    }
    @SuppressWarnings("unused")
    public Zlecenie(int id, int numer, String zleceniodawca, String przyjecie, String urzadzenie, String marka, String model, String seryjny, String usterka, String wady, String uwagi, String zakonczenie, String status) {
        Id = id;
        this.numer = numer;
        Zleceniodawca = zleceniodawca;
        Przyjecie = przyjecie;
        Urzadzenie = urzadzenie;
        Marka = marka;
        Model = model;
        Seryjny = seryjny;
        Usterka = usterka;
        Wady = wady;
        Uwagi = uwagi;
        Zakonczenie = zakonczenie;
        Status = status;
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
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public String getZleceniodawca() {
        return Zleceniodawca;
    }

    public void setZleceniodawca(String zleceniodawca) {
        Zleceniodawca = zleceniodawca;
    }

    public String getPrzyjecie() {
        return Przyjecie;
    }

    public void setPrzyjecie(String przyjecie) {
        Przyjecie = przyjecie;
    }

    public String getUrzadzenie() {
        return Urzadzenie;
    }

    public void setUrzadzenie(String urzadzenie) {
        Urzadzenie = urzadzenie;
    }

    public String getMarka() {
        return Marka;
    }

    public void setMarka(String marka) {
        Marka = marka;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getSeryjny() {
        return Seryjny;
    }

    public void setSeryjny(String seryjny) {
        Seryjny = seryjny;
    }

    public String getUsterka() {
        return Usterka;
    }

    public void setUsterka(String usterka) {
        Usterka = usterka;
    }

    public String getWady() {
        return Wady;
    }

    public void setWady(String wady) {
        Wady = wady;
    }

    public String getUwagi() {
        return Uwagi;
    }

    public void setUwagi(String uwagi) {
        Uwagi = uwagi;
    }

    public String getZakonczenie() {
        return Zakonczenie;
    }

    public void setZakonczenie(String zakonczenie) {
        Zakonczenie = zakonczenie;
    }
    public String getStatus(){return Status;}
    public void setStatus(String status){Status = status;}
}
