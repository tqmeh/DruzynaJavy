package com.example.Javaproject1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;

@Component
public class Rejestruj extends JFrame {
    JLabel lLogin,lHaslo,lPowtorzHaslo,lMail,lPowtorzMail;
    LimitowanyText tLogin,tMail,tPowtorzMail;
    JPasswordField pHaslo,pPowtorzHaslo;

    JButton bRejestruj,bWyjdz;
    String login,Haslo,Mail,PowtorzHaslo,PowtorzMail,SprawdzmyLogin;

    String loginhasz;
    PersonRepository personRepository;
     PasswordEncoder passwordEncoder;
     EncryptionService encryptionService;
    @Autowired
    public Rejestruj(PersonRepository personRepository,PasswordEncoder passwordEncoder,EncryptionService encryptionService)
    {

        this.personRepository=personRepository;
        this.passwordEncoder=passwordEncoder;
        this.encryptionService=encryptionService;
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(null);
        lLogin=new JLabel();
        lHaslo=new JLabel();
        lPowtorzHaslo=new JLabel();
        lMail=new JLabel();
        lPowtorzMail=new JLabel();
        tLogin=new LimitowanyText(20,false);
        pHaslo=new JPasswordField();
        pPowtorzHaslo=new JPasswordField();
        tMail=new LimitowanyText(40,false);
        tPowtorzMail=new LimitowanyText(40,false);
        bRejestruj=new JButton();
        bWyjdz=new JButton();

        StowrzNapis(lLogin,KontrolerJezyka.resourceBundle.getString("login"),20,10,100,20);
        StowrzNapis(lHaslo,KontrolerJezyka.resourceBundle.getString("haslo"),20,40,100,20);
        StowrzNapis(lPowtorzHaslo,KontrolerJezyka.resourceBundle.getString("powtorzHaslo"),20,70,100,20);
        StowrzNapis(lMail,KontrolerJezyka.resourceBundle.getString("mail"),20,100,100,20);
        StowrzNapis(lPowtorzMail,KontrolerJezyka.resourceBundle.getString("powtorzMail"),20,130,100,20);


        StworzWpis(tLogin,120,10,100,20);
        StworzWpis(tMail,120,100,100,20);
        StworzWpis(tPowtorzMail,120,130,100,20);


        StworzHaslo(pHaslo,120,40,100,20);
        StworzHaslo(pPowtorzHaslo,120,70,100,20);

        StworzPrzycisk(bRejestruj,KontrolerJezyka.resourceBundle.getString("rejestruj"), 100,170,100,20);
        StworzPrzycisk(bWyjdz,KontrolerJezyka.resourceBundle.getString("wyjdz"),200,170,100,20);

        bWyjdz.addActionListener(e ->
            dispose()
        );

        bRejestruj.addActionListener(e -> {

            pobierz();
            SprawdzLogin();
            loginhasz=encryptionService.encrypt(login);
            if(login.isEmpty())
            {
                Logowanie.WyswietlKomunikatoBledzie(KontrolerJezyka.resourceBundle.getString("niewpisanyLogin"));
            }
            else if (Haslo.isEmpty())
            {
                Logowanie.WyswietlKomunikatoBledzie(KontrolerJezyka.resourceBundle.getString("niewpisaneHaslo"));
            }
            else if (PowtorzHaslo.isEmpty())
            {
                Logowanie.WyswietlKomunikatoBledzie(KontrolerJezyka.resourceBundle.getString("niewpisanePonowneHaslo"));
            }
            else if (Mail.isEmpty())
            {
                Logowanie.WyswietlKomunikatoBledzie(KontrolerJezyka.resourceBundle.getString("niewpisanyMail"));
            }
            else if (PowtorzMail.isEmpty())
            {
                Logowanie.WyswietlKomunikatoBledzie(KontrolerJezyka.resourceBundle.getString("niewpisanyPonownyMail"));
            } else if (!Haslo.equals(PowtorzHaslo))
            {
                Logowanie.WyswietlKomunikatoBledzie(KontrolerJezyka.resourceBundle.getString("rozneHasla"));
            }
            else if(!Mail.equals(PowtorzMail))
            {
                Logowanie.WyswietlKomunikatoBledzie(KontrolerJezyka.resourceBundle.getString("rozneMaile"));
            }
            else if(login.equals(SprawdzmyLogin))
            {
                Logowanie.WyswietlKomunikatoBledzie(KontrolerJezyka.resourceBundle.getString("istniejacyLogin"));
            }

            else
            {
                System.out.println(loginhasz);
                System.out.println(Haslo);
                System.out.println(Mail);
                String ZahaszowaneHaslo=passwordEncoder.encode(Haslo);
                Person person=new Person(login,ZahaszowaneHaslo,Mail);
                personRepository.save(person);
                dispose();
            }


        });
    }
public void SprawdzLogin()
{
    List<Person> dane=personRepository.findAllByLogin(login);
    for(Person person:dane)
    {
        System.out.println(KontrolerJezyka.resourceBundle.getString("istniejacyLogin")+person.getlogin());
        SprawdzmyLogin=person.getlogin();
    }
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
    public void StworzWpis(JTextField textField,int a,int b,int c, int d)
    {
        textField.setBounds(a,b,c,d);
        add(textField);
    }

    public void StworzHaslo(JPasswordField passwordField,int a,int b,int c,int d)
    {
        passwordField.setBounds(a,b,c,d);
        add(passwordField);
    }
    public void pobierz()
    {
        login=tLogin.getText().trim();
        char[] Haslochar=pHaslo.getPassword();
        Haslo=new String(Haslochar).trim();
        Mail=tMail.getText().trim();
        PowtorzMail=tPowtorzMail.getText().trim();
        char[] PowtorzHaslochar=pPowtorzHaslo.getPassword();
        PowtorzHaslo=new String(PowtorzHaslochar).trim();

    }

    /*public void WyswietlKomunikatoBledzie(String Blad)
    {
        JOptionPane.showMessageDialog(
                null,
                KontrolerJezyka.resourceBundle.getString(Blad),
                "Błąd",
                JOptionPane.ERROR_MESSAGE
        );
    }*/

public void Czysc()
{
    tLogin.setText("");
    pHaslo.setText("");
    pPowtorzHaslo.setText("");
    tMail.setText("");
    tPowtorzMail.setText("");

}
}
