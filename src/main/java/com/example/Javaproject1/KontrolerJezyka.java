package com.example.Javaproject1;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class KontrolerJezyka extends JFrame {

    static Locale locale_pl_PL = new Locale("pl", "PL");
    static Locale locale_en_UK = new Locale("en", "UK");
    static ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle", locale_pl_PL);

    //Funkcja wywolywana przy naciesnieciu przycisku do zmiany jezyka z dowolnego miejsca w aplikacji
    public static void ZmianaJezykaNaPolski(){
        ResourceBundle.clearCache();
        resourceBundle = ResourceBundle.getBundle("bundle", locale_pl_PL);
        Locale.setDefault(locale_pl_PL);
        //System.out.println(resourceBundle.getLocale());

    }

    public static void ZmianaJezykaNaAngielski() {
        ResourceBundle.clearCache();
        resourceBundle = ResourceBundle.getBundle("bundle", locale_en_UK);
        Locale.setDefault(locale_en_UK);
    }
}
