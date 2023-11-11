package com.example.Javaproject1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.security.SecureRandom;
import java.util.Optional;


@Component
public class Logowanie extends JFrame {
    Rejestruj rejestruj;
    JTextField tLogin, tKodPotwierdzajacy;
    JPasswordField pHaslo;
    PersonRepository personRepository;
    JavaMailSender javaMailSender;
    static  String charakter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    private static final int dlugoscKodu = 10;
    String kodprzykladowy = GenerujKod(), kodUwierzytelniajacy;
    JLabel lLogin, lHaslo, lKodPotwierdzajacy;
    JButton bWyslij, bRejestruj, bZaloguj, bResetuj;
    Glowna glowna;
    public String Haslo, Login;
    ZlecenieGUI zlecenieGUI;
    PasswordEncoder passwordEncoder;

    @Autowired
    public Logowanie(Rejestruj rejestruj, PersonRepository personRepository, JavaMailSender javaMailSender, Glowna glowna, ZlecenieGUI zlecenieGUI, PasswordEncoder passwordEncoder) {

        this.rejestruj = rejestruj;
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
        this.javaMailSender = javaMailSender;
        this.glowna = glowna;
        this.zlecenieGUI = zlecenieGUI;
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(null);

        lKodPotwierdzajacy = new JLabel();
        lLogin = new JLabel();
        lHaslo = new JLabel();
        bWyslij = new JButton();
        bRejestruj = new JButton();
        bZaloguj = new JButton();
        tLogin = new JTextField();
        tKodPotwierdzajacy = new JTextField();
        pHaslo = new JPasswordField();
        bResetuj = new JButton();

        StowrzNapis(lLogin, "Login", 50, 10, 100, 20);
        StowrzNapis(lHaslo, "Haslo", 50, 40, 100, 20);

        StworzWpis(tLogin, 150, 10, 100, 20);

        StworzHaslo(pHaslo, 150, 40, 100, 20);

        StworzPrzycisk(bWyslij, "Wyślij", 50, 100, 100, 20);
        bWyslij.setVisible(true);
        StworzPrzycisk(bZaloguj, "Zaloguj", 50, 100, 100, 20);
        bZaloguj.setVisible(false);

        StworzPrzycisk(bRejestruj, "Rejestruj", 150, 100, 100, 20);
        bRejestruj.addActionListener(e -> {
            if (rejestruj != null) {
                rejestruj.setVisible(true);
            }
        });
        StworzPrzycisk(bResetuj, "Resetuj", 250, 100, 100, 20);
        bResetuj.addActionListener(e -> {
            ResetujGUI resetujGUI = new ResetujGUI(personRepository, javaMailSender,passwordEncoder);
            resetujGUI.setVisible(true);
        });


        bWyslij.addActionListener(e -> {
            Pobierz();

            Optional<Person> optionalPerson = personRepository.findByLogin(Login);
            if (optionalPerson.isPresent()) {
                Person person = optionalPerson.get();
                if (passwordEncoder.matches(Haslo, person.getHaslo())) {


                    WyslijMail(person.getMail());
                    revalidate();
                    repaint();

                    bWyslij.setVisible(false);
                    lKodPotwierdzajacy.setVisible(true);
                    tKodPotwierdzajacy.setVisible(true);
                    bZaloguj.setVisible(true);
                    StowrzNapis(lKodPotwierdzajacy, "Kod", 50, 70, 100, 20);
                    StworzWpis(tKodPotwierdzajacy, 150, 70, 100, 20);


                } else {

                    WyswietlKomunikatoBledzie("Błędne hasło");

                }
            } else {

                WyswietlKomunikatoBledzie("Brak użytkownika o podanym loginie");

            }
        });

        bZaloguj.addActionListener(e ->
                SprawdzKod(kodprzykladowy, tKodPotwierdzajacy)

        );
    }




    public void StowrzNapis(JLabel label,String napis,int a, int b, int c, int d)
    {
        label.setText(napis);
        label.setBounds(a,b,c,d);
        add(label);

    }

    public void StworzPrzycisk(JButton button,String napis,int a,int b,int c, int d)
    {
        button.setText(napis);
        button.setBounds(a,b,c,d);
        add(button);
    }

    public void StworzWpis(JTextField textField,int a,int b, int c,int d)
    {
        textField.setBounds(a,b,c,d);
        add(textField);
    }
    public void StworzHaslo(JPasswordField passwordField, int a,int b,int c, int d)
    {
        passwordField.setBounds(a,b,c,d);
        add(passwordField);
    }


    public void Pobierz()
    {
        char[] Haslochar=pHaslo.getPassword();
        Haslo=new String(Haslochar).trim();
        Login=tLogin.getText().trim();
        System.out.println(Haslo);
        System.out.println(Login);
    }
    public void WyswietlKomunikatoBledzie(String Blad)
    {
        JOptionPane.showMessageDialog(
                null,
                Blad,
                "Błąd",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void WyslijMail(String mail)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("druzynajavy@gmail.com");
        message.setTo(mail);
        message.setSubject("Kod uwierzytelniający zalogowanie");
        message.setText("Witaj, jesteś już prawie zalogowany, aby dokończyć logowanie wpisz w programie ten kod   "+kodprzykladowy+""); // Treść wiadomości

        javaMailSender.send(message);
    }

    public static String GenerujKod()
    {
        SecureRandom random=new SecureRandom();
        StringBuilder kod=new StringBuilder(dlugoscKodu);

        for(int i=0;i<dlugoscKodu;i++)
        {
            int kod1=random.nextInt(charakter.length());
            kod.append(charakter.charAt(kod1));
        }
        return kod.toString();
    }
    public void SprawdzKod(String napis,JTextField jTextField)
    {
        kodUwierzytelniajacy=jTextField.getText();
        if(napis.equals(kodUwierzytelniajacy))
        {
            System.out.println("Kod Poprawny");
            if (glowna != null) {
                glowna.setVisible(true);
                dispose();
            }
        }
        else
        {
            WyswietlKomunikatoBledzie("Wpisany kod uwierzytelniający jest niepoprawny");
        }
    }

}
