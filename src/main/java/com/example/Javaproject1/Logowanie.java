package com.example.Javaproject1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Locale;
import java.util.ResourceBundle;


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
    JLabel lLogin, lHaslo, lKodPotwierdzajacy, lJezyki;
    JButton bWyslij, bRejestruj, bZaloguj, bResetuj, bPolski, bAngielski;

    JComboBox cJezyki;
    String[] jezyki={"Polski","Angielski"};;
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
        setSize(400, 300);
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
        bPolski = new JButton();
        bAngielski = new JButton();

        lJezyki=new JLabel(JavaProjektApplication.resourceBundle.getString("jezyk")+":");
        lJezyki.setBounds(100,20,50,20);
        add(lJezyki);
        StworzPrzycisk(bPolski,"polski",150,20,100,20);
        bPolski.setVisible(true);
        StworzPrzycisk(bAngielski, "angielski",260,20,100,20);
        bAngielski.setVisible(true);

        StowrzNapis(lLogin, "login", 50, 60, 100, 20);
        StowrzNapis(lHaslo, "haslo", 50, 90, 100, 20);

        StworzWpis(tLogin, 150, 60, 100, 20);

        StworzHaslo(pHaslo, 150, 90, 100, 20);

        StworzPrzycisk(bWyslij, "wyslij", 50, 150, 100, 20);
        bWyslij.setVisible(true);
        StworzPrzycisk(bZaloguj, "zaloguj", 50, 150, 100, 20);
        bZaloguj.setVisible(false);

        StworzPrzycisk(bRejestruj, "rejestruj", 150, 150, 100, 20);
        bRejestruj.addActionListener(e -> {
            if (rejestruj != null) {
                rejestruj.Czysc();
                rejestruj.setVisible(true);
            }
        });
        StworzPrzycisk(bResetuj, "rejestruj", 250, 150, 100, 20);
        bResetuj.addActionListener(e -> {
            ResetujGUI resetujGUI = new ResetujGUI(personRepository, javaMailSender,passwordEncoder);
            resetujGUI.setVisible(true);
        });

        StowrzNapis(DarkModeHandler.lDarkMode,"jasny", 50,200,75,20);
        DarkModeHandler.bDarkMode.setLocation(100,200);
        DarkModeHandler.bDarkMode.setVisible(true);


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
                    System.out.println("Haslo to "+Haslo);
                    WyswietlKomunikatoBledzie("Błędne hasło");

                }
            } else {

                WyswietlKomunikatoBledzie("Brak użytkownika o podanym loginie");

            }
        });

        bZaloguj.addActionListener(e ->
                SprawdzKod(kodprzykladowy, tKodPotwierdzajacy)

        );

        bPolski.addActionListener (e -> {
                ResourceBundle.clearCache();
            Locale.setDefault(JavaProjektApplication.locale_pl_PL);
            JavaProjektApplication.resourceBundle = ResourceBundle.getBundle("bundle", JavaProjektApplication.locale_pl_PL);
            invalidate();
            validate();
            repaint();
            setVisible(true);
            System.out.println("Polski");
            System.out.println("Resource bundle po naciśnięciu Polski: "+JavaProjektApplication.resourceBundle.getLocale());
        });

        bAngielski.addActionListener (e -> {
            ResourceBundle.clearCache();
            Locale.setDefault(JavaProjektApplication.locale_pl_PL);
            JavaProjektApplication.resourceBundle = ResourceBundle.getBundle("bundle", JavaProjektApplication.locale_en_UK);
            invalidate();
            validate();
            repaint();
            setVisible(true);
            System.out.println("Angielski");
            System.out.println("Resource bundle po naciśnięciu Angielski: "+JavaProjektApplication.resourceBundle.getLocale());
        });

    }




    public void StowrzNapis(JLabel label,String napis,int a, int b, int c, int d)
    {
        napis=JavaProjektApplication.resourceBundle.getString(napis);
        label.setText(napis);
        label.setBounds(a,b,c,d);
        add(label);

    }

    public void StworzPrzycisk(JButton button,String napis,int a,int b,int c, int d)
    {
        napis=JavaProjektApplication.resourceBundle.getString(napis);
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
