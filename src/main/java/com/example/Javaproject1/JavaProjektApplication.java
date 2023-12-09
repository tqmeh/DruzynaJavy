package com.example.Javaproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;


@SpringBootApplication
    @Import(MailConfig.class)
    public class JavaProjektApplication {

        public static void main(String[] args) {


            System.setProperty("java.awt.headless", "false");


            ConfigurableApplicationContext context = SpringApplication.run(JavaProjektApplication.class, args);
            Logowanie logowanie = context.getBean(com.example.Javaproject1.Logowanie.class);
            logowanie.setVisible(true);
        }

    }


