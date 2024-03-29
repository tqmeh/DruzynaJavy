package com.example.Javaproject1;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import javax.swing.*;


@SpringBootApplication
@Import(MailConfig.class)
public class JavaProjektApplication {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }

        System.setProperty("java.awt.headless", "false");


        ConfigurableApplicationContext context = SpringApplication.run(JavaProjektApplication.class, args);
        Logowanie logowanie = context.getBean(com.example.Javaproject1.Logowanie.class);
        logowanie.setVisible(true);
    }

}
/*
* dodanie przycisku do okna głównego
* otwiera nowe okno
* w nim przyciski faktura i raport
* przycisk faktura:
* otwiera okno które zbiera info: zleceniodawca, zlecenie
* pokazuje podgląd i daje możliwość wyboru: zapisz jako pdf, drukuj.
* Raport - to samo. (wszystkie niezależnie jaki zleceniodawca)
*
* */


