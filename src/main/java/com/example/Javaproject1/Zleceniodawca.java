package com.example.Javaproject1;

import jakarta.persistence.*;

@Entity
@Table
public class Zleceniodawca {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String nazwa;
    private String pelna;
    private String miasto;
    private String kod;
    private String ulica;
    private String dom;
    private String mieszkanie;
    private String kraj;
    private String nip;
    private String stacjonarny;
    private String komorkowy;

    public Zleceniodawca()
    {

    }
    @SuppressWarnings("unused")
    public Zleceniodawca(int id, String nazwa, String pelna, String miasto, String kod, String ulica, String dom, String mieszkanie, String kraj, String nip, String stacjonarny, String komorkowy) {
        Id = id;
        this.nazwa = nazwa;
        this.pelna = pelna;
        this.miasto = miasto;
        this.kod = kod;
        this.ulica = ulica;
        this.dom = dom;
        this.mieszkanie = mieszkanie;
        this.kraj = kraj;
        this.nip = nip;
        this.stacjonarny = stacjonarny;
        this.komorkowy = komorkowy;
    }

    public int getId() {
        return Id;
    }



    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getPelna() {
        return pelna;
    }

    public void setPelna(String pelna) {
        this.pelna = pelna;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getMieszkanie() {
        return mieszkanie;
    }

    public void setMieszkanie(String mieszkanie) {
        this.mieszkanie = mieszkanie;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getStacjonarny() {
        return stacjonarny;
    }

    public void setStacjonarny(String stacjonarny) {
        this.stacjonarny = stacjonarny;
    }

    public String getKomorkowy() {
        return komorkowy;
    }

    public void setKomorkowy(String komorkowy) {
        this.komorkowy = komorkowy;
    }

    @Override
    public String toString() {
        return "Zleceniodawca{"
                + Id
                 + nazwa + '\''
                 + pelna + '\'' +
                 miasto + '\'' +
                 kod + '\'' +
                 ulica + '\'' +
                  dom + '\'' +
                 mieszkanie + '\'' +
                 kraj + '\'' +
                 nip + '\'' +
                 stacjonarny + '\'' +
                 komorkowy + '\'' +
                '}';
    }


    public String daneZleceniodawcy(EncryptionService encryptionService) {
        return "Zleceniodawca:\n" +
                " nazwa: " + encryptionService.decrypt(nazwa) + '\n' +
                "pelna nazwa:" + encryptionService.decrypt(pelna )+ '\n' +
                "miasto:" + encryptionService.decrypt(miasto )+ '\n' +
                "kod:" + encryptionService.decrypt(kod) + '\n' +
                "ulica: " + encryptionService.decrypt(ulica) + '\n' +
                "dom:" + encryptionService.decrypt(dom) + '\n' +
                "mieszkanie: " + encryptionService.decrypt(mieszkanie) + '\n' +
                "kraj: " + encryptionService.decrypt(kraj) + '\n' +
                "nip: " + encryptionService.decrypt(nip) + '\n' +
                "stacjonarny: " + encryptionService.decrypt(stacjonarny) + '\n' +
                "komorkowy: " + encryptionService.decrypt(komorkowy) + '\n';
    }
}
