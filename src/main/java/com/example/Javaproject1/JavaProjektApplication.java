package com.example.Javaproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import java.util.Locale;
import java.util.ResourceBundle;


@SpringBootApplication
    @Import(MailConfig.class)
    public class JavaProjektApplication {

        public static Locale locale_pl_PL = new Locale("pl", "PL");
        public static Locale locale_en_UK = new Locale("en", "UK");
        public static ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle", JavaProjektApplication.locale_pl_PL);

        public static void main(String[] args) {


            System.setProperty("java.awt.headless", "false");


            ConfigurableApplicationContext context = SpringApplication.run(JavaProjektApplication.class, args);
            Logowanie logowanie = context.getBean(com.example.Javaproject1.Logowanie.class);
            logowanie.setVisible(true);
        }

    }


