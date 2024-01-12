package com.example.Javaproject1;
import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
//Trzeba dodac plik "vflatlaf-intellij-themes-3.2.5.jar"
//Ktory znajduje sie w \src\main\resources\

public class DarkModeHandler {
    //Flagi dla klasy Glowna.java
    static boolean darkmode, lightmode;

    static void ZmianaTrybuNaCiemny() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            darkmode=true;
            lightmode=false;
        } catch (UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }
    }

    static void ZmianaTrybuNaJasny(){
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            darkmode=false;
            lightmode=true;
        } catch (UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }
    }

}