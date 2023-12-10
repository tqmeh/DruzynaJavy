package com.example.Javaproject1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.security.SecureRandom;
import java.sql.Array;
import java.util.*;


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
    JLabel lLogin, lHaslo, lKodPotwierdzajacy, lJezyki, lLightDarkMode;
    JButton bWyslij, bRejestruj, bZaloguj, bResetuj, bPolski, bAngielski, bDarkMode, bLightMode;

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
        lLightDarkMode = new JLabel();
        bDarkMode = new JButton();
        bLightMode = new JButton();

        lJezyki=new JLabel(KontrolerJezyka.resourceBundle.getString("jezyk")+":");
        lJezyki.setBounds(50,20,100,20);
        add(lJezyki);
        StworzPrzycisk(bPolski,"polski",150,20,100,20);
        bPolski.setVisible(true);
        StworzPrzycisk(bAngielski, "angielski",260,20,100,20);
        bAngielski.setVisible(true);

        StowrzNapis(lLogin, "login", 50, 70, 100, 20);
        StowrzNapis(lHaslo, "haslo", 50, 100, 100, 20);

        StworzWpis(tLogin, 150, 70, 100, 20);

        StworzHaslo(pHaslo, 150, 100, 100, 20);

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

        StowrzNapis(lLightDarkMode, "tryb", 20, 200, 130,20);
        StworzPrzycisk(bLightMode, "lightButton", 150,200,100,20);
        bLightMode.setVisible(true);
        StworzPrzycisk(bDarkMode, "darkButton", 250,200,100,20);
        bDarkMode.setVisible(true);

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
                    StowrzNapis(lKodPotwierdzajacy, "kod", 50, 70, 100, 20);
                    StworzWpis(tKodPotwierdzajacy, 150, 70, 100, 20);


                } else {
                    System.out.println("Haslo to "+Haslo);
                    WyswietlKomunikatoBledzie("Błędne hasło");

                }
            } else {

                WyswietlKomunikatoBledzie("brakLoginu");

            }
        });

        bZaloguj.addActionListener(e ->
                SprawdzKod(kodprzykladowy, tKodPotwierdzajacy)

        );


        ArrayList<JLabel> allLabels = new ArrayList<JLabel>();
        allLabels.add(lLogin);
        allLabels.add(lHaslo);
        allLabels.add(lKodPotwierdzajacy);
        allLabels.add(lJezyki);
        allLabels.add(lLightDarkMode);

        ArrayList<JButton> allButtons = new ArrayList<>();
        allButtons.add(bWyslij);
        allButtons.add(bRejestruj);
        allButtons.add(bZaloguj);
        allButtons.add(bResetuj);
        allButtons.add(bPolski);
        allButtons.add(bAngielski);
        allButtons.add(bDarkMode);
        allButtons.add(bLightMode);



        bPolski.addActionListener (e -> {
            //ResourceBundle.clearCache();
            //Locale.setDefault(KontrolerJezyka.locale_pl_PL);
            //KontrolerJezyka.resourceBundle = ResourceBundle.getBundle("bundle", KontrolerJezyka.locale_pl_PL);
            KontrolerJezyka.ZmianaJezykaNaPolski();
            revalidate();
            setVisible(true);
            System.out.println("Polski");
            System.out.println("Resource bundle po naciśnięciu Polski: "+KontrolerJezyka.resourceBundle.getLocale());
            for(JLabel label : allLabels){
                label.revalidate();
                label.repaint();
            }
            for(JButton button : allButtons){
                button.revalidate();
                button.repaint();
            }

        });

        bAngielski.addActionListener (e -> {
            //ResourceBundle.clearCache();
            //Locale.setDefault(KontrolerJezyka.locale_pl_PL);
            //KontrolerJezyka.resourceBundle = ResourceBundle.getBundle("bundle", KontrolerJezyka.locale_en_UK);
            KontrolerJezyka.ZmianaJezykaNaAngielski();
            invalidate();
            revalidate();
            setVisible(true);
            System.out.println("Angielski");
            System.out.println("Resource bundle po naciśnięciu Angielski: "+KontrolerJezyka.resourceBundle.getLocale());
            for(JLabel label : allLabels){
                label.revalidate();
                label.repaint();
            }
            for(JButton button : allButtons){
                button.revalidate();
                button.repaint();
            }

        });

        bLightMode.addActionListener(e -> {
            DarkModeHandler.ZmianaTrybuNaJasny();
            SwingUtilities.updateComponentTreeUI(this);
            revalidate();
            repaint();
        });

        bDarkMode.addActionListener(e -> {
            DarkModeHandler.ZmianaTrybuNaCiemny();
            SwingUtilities.updateComponentTreeUI(this);
            revalidate();
            repaint();
        });
    }



    public void StowrzNapis(JLabel label,String napis,int a, int b, int c, int d)
    {
        napis=KontrolerJezyka.resourceBundle.getString(napis);
        label.setText(napis);
        label.setBounds(a,b,c,d);
        add(label);

    }

    public void StworzPrzycisk(JButton button,String napis,int a,int b,int c, int d)
    {
        napis=KontrolerJezyka.resourceBundle.getString(napis);
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
    public static void WyswietlKomunikatoBledzie(String Blad)
    {
        JOptionPane.showMessageDialog(
                null,
                KontrolerJezyka.resourceBundle.getString(Blad),
                "Błąd",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void WyslijMail(String mail)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("druzynajavy@gmail.com");
        message.setTo(mail);
        message.setSubject(KontrolerJezyka.resourceBundle.getString("kodAutoryzacji"));
        message.setText(KontrolerJezyka.resourceBundle.getString("tekstMaila")+kodprzykladowy+""); // Treść wiadomości

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
            System.out.println(KontrolerJezyka.resourceBundle.getString("poprawnyKod"));
            if (glowna != null) {
                glowna.setVisible(true);
                dispose();
            }
        }
        else
        {
            WyswietlKomunikatoBledzie("blednyKod");
        }
    }

}
