package com.example.Javaproject1;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.*;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
public class ResetujGUI extends JFrame {

    JButton bZapisz,bWyjdz,bZapisz1;
    JLabel lLogin,lHaslo,lPowtorzHaslo,lKod;
    JTextField tLogin,tKod;
    JPasswordField pHaslo,pPowtorzHaslo;

    String Haslo,Login,PowtorzHaslo,SprawdzLogin;
      JavaMailSender javaMailSender;
    PersonRepository personRepository;
    private static final String charakter="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    private static final int dlugoscKodu=10;
   String kodprzykladowy=GenerujKod(),kodUwierzytelniajacy;
    PasswordEncoder passwordEncoder;
    public ResetujGUI(PersonRepository personRepository, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder)
    {
        this.personRepository=personRepository;
        this.javaMailSender=javaMailSender;
        this.passwordEncoder=passwordEncoder;
        setSize(400,200);
        setLayout(null);
        revalidate();
        lLogin=new JLabel();
        lHaslo=new JLabel();
        lPowtorzHaslo=new JLabel();
        tLogin=new JTextField();
        pHaslo=new JPasswordField();
        pPowtorzHaslo=new JPasswordField();
        bZapisz=new JButton();
        bWyjdz=new JButton();
        lKod=new JLabel();
        tKod=new JTextField();
        bZapisz1=new JButton();
        StworzNapis(lLogin,"login",50,10,100,20);
        StworzNapis(lHaslo,"haslo",50,40,100,20);
        StworzNapis(lPowtorzHaslo,"powtorzHaslo",50,70,100,20);
        StworzNapis(lKod,"kod",50,100,100,20);
        StworzWpis(tLogin,150,10,100,20);
        StworzWpis(tKod,150,100,100,20);
        StworzHaslo(pHaslo,150,40,100,20);
        StworzHaslo(pPowtorzHaslo,150,70,100,20);
        StworzPrzycisk(bZapisz,"zapisz",50,130,100,20);
        StworzPrzycisk(bWyjdz,"wyjdz",160,130,100,20);
        StworzPrzycisk(bZapisz1,"zapisz",50,130,100,20);
        lKod.setVisible(false);
        tKod.setVisible(false);
        bZapisz1.setVisible(false);

        bWyjdz.addActionListener(e -> dispose());

        bZapisz.addActionListener(e -> {
            Pobierz();
            SprawdzCzyLoginIstnieje();
            Sprawdz();


            Optional<Person> optionalPerson = personRepository.findByLogin(Login);
            if (optionalPerson.isPresent()) {
                Person person = optionalPerson.get();
                WyslijMail(person.getMail());
                bZapisz.setVisible(false);
                bZapisz1.setVisible(true);
            }
        });
        bZapisz1.addActionListener(e ->
            SprawdzKodWpisany()

        );
    }
    public void Pobierz()
    {
        Login=tLogin.getText().trim();
        char[] hasloArray = pHaslo.getPassword();
        Haslo = new String(hasloArray).trim();

        char[] powtorzHasloArray = pPowtorzHaslo.getPassword();
        PowtorzHaslo = new String(powtorzHasloArray).trim();
    }
    public void SprawdzKodWpisany()
    {
        kodUwierzytelniajacy=tKod.getText().trim();
        if(kodprzykladowy.equals(kodUwierzytelniajacy))
        {
            WyswietlKomunikatPotwierdzajacy("zmienioneHaslo");
            Zmien();

        }
        else
        {
          WyswietlKomunikatoBledzie("rozneHasla");
        }
    }
    public void Sprawdz()
    {
        if(Login.isEmpty())
        {
            WyswietlKomunikatoBledzie("niewpisanyLogin");
        }
        else if (Haslo.isEmpty())
        {
            WyswietlKomunikatoBledzie("niewpisanyHaslo");
        }
        else if (PowtorzHaslo.isEmpty())
        {
            WyswietlKomunikatoBledzie("niewpisanePonowneHaslo");
        }
        else if(!Haslo.equals(PowtorzHaslo))
        {
            WyswietlKomunikatoBledzie("rozneHasla");
        } else if (!Login.equals(SprawdzLogin))
        {
            WyswietlKomunikatoBledzie("brakLoginu");
        } else
        {
            lKod.setVisible(true);
            tKod.setVisible(true);
            bZapisz1.setVisible(true);
        }
    }

    public void SprawdzCzyLoginIstnieje()
    {
        List<Person> sprawdz=personRepository.findAllByLogin(Login);
        for(Person person: sprawdz)
        {
            SprawdzLogin=person.getlogin();
        }
    }
    public void Zmien()
    {
        List<Person> login=personRepository.findAllByLogin(Login);
        for(Person person:login)
        {
            String zahaszowane=passwordEncoder.encode(Haslo);
           person.setHaslo(zahaszowane);
           personRepository.save(person);
            dispose();
        }

    }
    public void WyswietlKomunikatoBledzie(String Blad)
    {
        JOptionPane.showMessageDialog(
                null,
                KontrolerJezyka.resourceBundle.getString(Blad),
                "Błąd",
                JOptionPane.ERROR_MESSAGE
        );
    }
    public void WyswietlKomunikatPotwierdzajacy(String Potwierdzenie)
    {
        JOptionPane.showMessageDialog(
                null,
                Potwierdzenie,
                "Potwierdzenie",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    public void StworzNapis(JLabel label,String napis,int a, int b, int c, int d)
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
    public void WyslijMail(String mail)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("druzynajavy@gmail.com");
        message.setTo(mail);
        message.setSubject("Kod uwierzytelniający zmiane hasla");
        message.setText("Witaj twoje hasło jest już prawie zmienione wpisz w programie ten kod  "+kodprzykladowy+"");

        javaMailSender.send(message);
    }


}
