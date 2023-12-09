package com.example.Javaproject1;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class KontrolerJezyka{

    static Locale locale_pl_PL = new Locale("pl", "PL");
    static Locale locale_en_UK = new Locale("en", "UK");
    static ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle", locale_en_UK);

    //Funkcja wywolywana przy naciesnieciu przycisku do zmiany jezyka z dowolnego miejsca w aplikacji
    public static void ZmianaJezykaNaPolski(){
        resourceBundle.clearCache();
        Locale.setDefault(locale_pl_PL);
        resourceBundle = ResourceBundle.getBundle("bundle", locale_pl_PL);
        //System.out.println(resourceBundle.getLocale());

    }

    public static void ZmianaJezykaNaAngielski() {
        resourceBundle.clearCache();
        Locale.setDefault(locale_en_UK);
        resourceBundle = ResourceBundle.getBundle("bundle", locale_en_UK);
    }
}
