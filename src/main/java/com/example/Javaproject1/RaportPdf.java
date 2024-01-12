package com.example.Javaproject1;

import java.util.ArrayList;
import java.util.List;
public class RaportPdf {

    private String nazwa;
    private List<String> naglowki = new ArrayList<>();
    private List<List<String>>rzedy = new ArrayList<>();
    private String opis = "";

    public RaportPdf(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setNaglowki(List<String> naglowki) {
        this.naglowki = naglowki;
    }

    public void dodajRzad(List<String> rzad){
        rzedy.add(rzad);
    }

    public List<String> getRzad(int i) {
        return rzedy.get(i);
    }

    public int ilosc(){
        return naglowki.size();
    }

    public String getNaglowek(int i){
        return naglowki.get(i);
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
