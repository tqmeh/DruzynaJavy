package com.example.Javaproject1;
import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
//Trzeba dodac plik "flatlaf-intellij-themes-3.2.5.jar"
//Ktory znajduje sie w \src\main\resources\

public class DarkModeHandler {
    public static JLabel lDarkMode, lLightMode;
    public static JButton bDarkMode, bLightMode;
    public static int indeksTrybu=1;
    //public static JToggleButton bDarkMode;

    public DarkModeHandler() {
        lDarkMode = new JLabel();
        lLightMode = new JLabel();
        bDarkMode = new JButton();
        bLightMode = new JButton();

        bDarkMode.addActionListener(e -> {
            try {
                if(indeksTrybu==1){
                    indeksTrybu=2;
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                }
            } catch (UnsupportedLookAndFeelException ex) {
                    throw new RuntimeException(ex);
            }
        });

        bLightMode.addActionListener(e -> {
            try {
                if(indeksTrybu==2){
                    indeksTrybu=1;
                    UIManager.setLookAndFeel(new FlatLightLaf());
                }
            } catch (UnsupportedLookAndFeelException ex) {
                throw new RuntimeException(ex);
            }
        });

        /*
        bDarkMode = new JToggleButton(String jasny) {

            this(jasny, null, false);
        }; */
    }
}
