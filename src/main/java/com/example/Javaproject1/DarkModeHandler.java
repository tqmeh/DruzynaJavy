package com.example.Javaproject1;
import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
//Trzeba dodac plik "flatlaf-intellij-themes-3.2.5.jar"
//Ktory znajduje sie w \src\main\resources\

public class DarkModeHandler {
    static void ZmianaTrybuNaCiemny() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }
    }

    static void ZmianaTrybuNaJasny(){
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }
    }

}