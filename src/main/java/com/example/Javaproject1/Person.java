package com.example.Javaproject1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SuppressWarnings("unused")
    private Long id;



    private String login;
    private String Haslo;
    private  String Mail;
    @SuppressWarnings("unused")
    public Person()
    {

    }
    public Person(String login, String haslo, String mail) {
        this.login = login;
        Haslo = haslo;
        Mail = mail;
    }
    public String getlogin() {
        return login;
    }
    @SuppressWarnings("unused")
    public void setLogin(String login) {
        this.login = login; // 'this' odnosi się do bieżącego obiektu
    }

    public String getHaslo() {
        return Haslo;
    }

    public void setHaslo(String haslo) {
        Haslo = haslo;
    }

    public String getMail() {
        return Mail;
    }
    @SuppressWarnings("unused")
    public void setMail(String mail) {
        Mail = mail;
    }
}
