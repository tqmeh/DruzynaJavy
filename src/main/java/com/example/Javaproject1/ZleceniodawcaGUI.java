package com.example.Javaproject1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;
import java.awt.*;

import java.util.Optional;



@Component
public class ZleceniodawcaGUI extends JFrame {
    JPanel panel1,panel2,panel3;
    JLabel lNazwaSkroconaZleceniodawcy,lZleceniodawca,lMiasto,lKodPocztowy,lUlica,lUkosnik,lNip,lTelefonKomorkowy,lTelefonStacjonarny;
   LimitowanyText tNazwaSkroconaZleceniodawcy,tMiasto,tKodPocztowy,tUlica,tNumerDomu,tNumerMieszkania,tKraj,tTelefonStacjonarny,tTelefonKomorkowy;
    String NazwaSkroconaZleceniodawcy,Miasto,KodPocztowy,Ulica,NumerDomu,NumerMieszkania,Nip,Kraj,TelefonStacjonarny,TelefonKomorkowy,PelnaNazwaZleceniodawcy;
    LimitowanyTextArea aPelnaNazwaZleceniodawcy;
    JButton bZapisz,bWyjdz;
    String SprawdzNazweSkrocona,SprawdzPelnaNazweZleceniodawcy;
    LimitowanyText tNip;
     EncryptionService encryptionService;

    String NazwaSkroconaZleceniodawcyhasz,PelnaNazwaZleceniodawcyhasz,Miastohasz,KodPocztowyhasz,Ulicahasz,NumerDomuhasz,NumerMieszkaniahasz,Krajhasz,Niphasz,TelefonStacjonarnyhasz,
    TelefonKomorkowyhasz;
int ID;
     ZleceniodawcaRepository zleceniodawcaRepository;
    String krotkanazwa;
    String dluganazwa;

    @Autowired
    public ZleceniodawcaGUI(ZleceniodawcaRepository zleceniodawcaRepository, EncryptionService encryptionService )
    {
        this.zleceniodawcaRepository=zleceniodawcaRepository;
        this.encryptionService=encryptionService;
        setSize(400,200);
        setExtendedState(MAXIMIZED_BOTH);
        revalidate();
        panel1=stworzPanel1();
        panel2=stworzPanel2();
        panel3=stworzPanel3();

    }

    private JPanel stworzPanel1()
    {
        JPanel panel=new JPanel();
//        panel.setBackground(Color.BLACK);
        panel.setPreferredSize(new Dimension(100,200));
        add(panel,BorderLayout.WEST);

        bZapisz=new JButton();
        bWyjdz=new JButton();

        StworzPrzycisk(bZapisz,"zapisz",100,20,panel);
        StworzPrzycisk(bWyjdz,"wyjdz",100,20,panel);




        bZapisz.addActionListener(e -> {

            Pobierz();
            Szyfruj();

            Sprawdz();


        });
        bWyjdz.addActionListener(e ->
            dispose()
        );




        return panel;
    }
    private JPanel stworzPanel2()
    {
        JPanel panel=new JPanel();
//        panel.setBackground(Color.orange);
        panel.setPreferredSize(new Dimension(100,100));
        add(panel,BorderLayout.NORTH);

        return panel;
    }
    private JPanel stworzPanel3()
    {
        JPanel panel=new JPanel();
//        panel.setBackground(Color.red);
        add(panel,BorderLayout.CENTER);
        panel.setLayout(null);
        lNazwaSkroconaZleceniodawcy=new JLabel();
        lZleceniodawca=new JLabel();
        lMiasto=new JLabel();
        lKodPocztowy=new JLabel();
        lUlica=new JLabel();
        lUkosnik=new JLabel();
        lNip=new JLabel();

        lTelefonStacjonarny=new JLabel();
        lTelefonKomorkowy=new JLabel();

        tNazwaSkroconaZleceniodawcy=new LimitowanyText(20,false);
        tKodPocztowy=new LimitowanyText(6,false);
        tUlica=new LimitowanyText(20,false);
        tUlica.setCaretPosition(0);
        tNumerDomu=new LimitowanyText(5,false);
        tNumerDomu.setCaretPosition(0);
        tNumerMieszkania=new LimitowanyText(5,false);
        tNumerMieszkania.setCaretPosition(0);
        tTelefonStacjonarny=new LimitowanyText(16,true);
        tTelefonStacjonarny.setCaretPosition(0);
        tNip=new LimitowanyText(13,true);
        tNip.setCaretPosition(0);
        tKraj=new LimitowanyText(2,false);
        tKraj.setCaretPosition(0);
        tTelefonKomorkowy=new LimitowanyText(16,true);
        tTelefonKomorkowy.setCaretPosition(0);
        tMiasto=new LimitowanyText(40,false);
        tMiasto.setCaretPosition(0);
        aPelnaNazwaZleceniodawcy=new LimitowanyTextArea(100);


        WysweitlNapis(lNazwaSkroconaZleceniodawcy,"skroconaNazwaZleceniodawcy",20,0,200,20,panel);
        WysweitlNapis(lZleceniodawca,"pelnaNazwaZleceniodawcy",20,50,200,20,panel);
        WysweitlNapis(lMiasto,"miasto",20,240,100,20,panel);
        WysweitlNapis(lKodPocztowy,"kodPocztowy",20,290,80,20,panel);
        WysweitlNapis(lUlica,"ulica",20,320,80,20,panel);
        WysweitlNapis(lUkosnik,"slash",205,340,5,20,panel);
        WysweitlNapis(lNip,"NIP",400,20,40,20,panel);
        WysweitlNapis(lTelefonStacjonarny,"telefonStacjonarny",400,50,150,20,panel);
        WysweitlNapis(lTelefonKomorkowy,"telefonKomorkowy",400,110,150,20,panel);

        StworzTextArene(aPelnaNazwaZleceniodawcy,20,80,200,100,panel);

        WpiszTekst(tNazwaSkroconaZleceniodawcy,20,20,200,20,panel);
        WpiszTekst(tMiasto,20,260,200,20,panel);
        WpiszTekst(tKodPocztowy,110,290,110,20,panel);
        WpiszTekst(tUlica,20,340,150,20,panel);
        WpiszTekst(tNumerDomu,180,340,20,20,panel);
        WpiszTekst(tNumerMieszkania,210,340,20,20,panel);
        WpiszTekst(tKraj,430,20,20,20,panel);
        WpiszTekst(tNip,455,20,100,20,panel);
        WpiszTekst(tTelefonStacjonarny,400,80,150,20,panel);
        WpiszTekst(tTelefonKomorkowy,400,140,150,20,panel);







        return panel;
    }
    public void WysweitlNapis(JLabel label,String napis,int a, int b,int c, int d, JPanel panel)
    {
        napis=KontrolerJezyka.resourceBundle.getString(napis);
        label.setText(napis);
        label.setBounds(a,b,c,d);
        panel.add(label);
    }
    public void WpiszTekst(JTextField text,int a,int b,int c,int d,JPanel panel)
    {

        text.setBounds(a,b,c,d);
        text.requestFocus();
        text.setCaretPosition(0);

        panel.add(text);
    }

    public void Pobierz()
    {

        NazwaSkroconaZleceniodawcy=tNazwaSkroconaZleceniodawcy.getText().trim();
        Miasto=tMiasto.getText().trim();
        KodPocztowy=tKodPocztowy.getText().trim();
        Ulica=tUlica.getText().trim();
        NumerDomu=tNumerDomu.getText().trim();
        NumerMieszkania=tNumerMieszkania.getText().trim();
        Nip=tNip.getText().trim();
        Kraj=tKraj.getText().trim();
        TelefonStacjonarny=tTelefonStacjonarny.getText().trim();
        TelefonKomorkowy=tTelefonKomorkowy.getText().trim();
        PelnaNazwaZleceniodawcy=aPelnaNazwaZleceniodawcy.getText().trim();

        System.out.println("dziala pobierz");



    }
    public void Sprawdzmy()
    {

        // Pobranie wszystkich obiektów Zleceniodawca
        List<Zleceniodawca> wszystkieZleceniodawcy = zleceniodawcaRepository.findAll();

        // Odszyfrowanie i wyświetlenie nazw
        List<String> odszyfrowaneNazwy = wszystkieZleceniodawcy.stream()
                .map(zleceniodawca -> encryptionService.decrypt(zleceniodawca.getNazwa()))
                .toList();

        // Wyświetlenie odszyfrowanych nazw
        for (String odszyfrowanaNazwa : odszyfrowaneNazwy) {
            System.out.println(odszyfrowanaNazwa);
        }
        if(odszyfrowaneNazwy.contains(NazwaSkroconaZleceniodawcy))
        {
            SprawdzNazweSkrocona=NazwaSkroconaZleceniodawcy;
            System.out.println("Istnieje taka nazwa");
        }

    }
    public void Sprawdzmy1()
    {


        List<Zleceniodawca> wszyscy=zleceniodawcaRepository.findAll();
        List<String> odszyfrowanieNazwy1=wszyscy.stream().map(zleceniodawca -> encryptionService.decrypt(zleceniodawca.getNazwa())).toList();
        for(String odszyfrowanieNazwa:odszyfrowanieNazwy1)
        {
            System.out.println(odszyfrowanieNazwa);
        }
        if(odszyfrowanieNazwy1.contains(PelnaNazwaZleceniodawcy))
        {
            SprawdzPelnaNazweZleceniodawcy=PelnaNazwaZleceniodawcy;
            System.out.println("Taka nazwa istnieje");
        }
    }

    public void StworzPrzycisk(JButton button,String nazwa,int a,int b,JPanel panel)
    {
        nazwa=KontrolerJezyka.resourceBundle.getString(nazwa);
        button.setPreferredSize(new Dimension(a,b));
        button.setText(nazwa);
        panel.add(button);
    }
    public void StworzTextArene(JTextArea area,int a,int b, int c, int d, JPanel panel)
    {
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setCaretPosition(0);
        area.setBounds(a,b,c,d);
        panel.add(area);
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
    public void Sprawdz()
    {
        String nazwamala=NazwaSkroconaZleceniodawcy;
        System.out.println("Dziala sprawdz");
        if(NazwaSkroconaZleceniodawcy.isEmpty())
        {
            WyswietlKomunikatoBledzie("Nie wpisałeś nazwy skróconej zleceniodawcy");
        }
        else if (PelnaNazwaZleceniodawcy.isEmpty())
        {
            WyswietlKomunikatoBledzie("Nie wpisałeś pełnej nazwy zleceniodawcy");
        }
        else if (Miasto.isEmpty())
        {
            WyswietlKomunikatoBledzie("Nie wpisaleś Miasta");
        }
        else if (KodPocztowy.isEmpty())
        {
            WyswietlKomunikatoBledzie("Nie wpisałeś kodu pocztowego");
        }
        else if (Ulica.isEmpty())
        {
            WyswietlKomunikatoBledzie("Nie wpisałeś ulicy");
        }
        else if (NumerDomu.isEmpty())
        {
            WyswietlKomunikatoBledzie("Nie wpisałeś numeru domu");
        }

         /*  else if(Kraj.isEmpty())
            {
                WyswietlKomunikatoBledzie("Nie wpisałeś skrótu kraju");
            }
            else if (Nip.isEmpty())
            {
                WyswietlKomunikatoBledzie("Nie wpisałeś NIPu firmy");
            }
            */



        else if (TelefonStacjonarny.isEmpty())
        {
            if(TelefonKomorkowy.isEmpty())
            {
                WyswietlKomunikatoBledzie("Nie wpisaleś numeru telefonu komórkowego bądź stacjoanrnego");
            }
        }
       /* else if (NazwaSkroconaZleceniodawcy.equals(SprawdzNazweSkrocona))
        {
                WyswietlKomunikatoBledzie("Zleceniodawca o takiej nazwie skróconej już istnieje");
        }
        else if (PelnaNazwaZleceniodawcy.equals(SprawdzPelnaNazweZleceniodawcy))
        {
            WyswietlKomunikatoBledzie("ZLeceniodawca o takiej pełnej nazwie już istnieje");
        }

        */
        else if (!nazwamala.isEmpty())
        {
            WyslijdoBazy();

        }

    }
    public void Szyfruj()
    {
        NazwaSkroconaZleceniodawcyhasz=encryptionService.encrypt(NazwaSkroconaZleceniodawcy);
        PelnaNazwaZleceniodawcyhasz=encryptionService.encrypt(PelnaNazwaZleceniodawcy);
        Miastohasz=encryptionService.encrypt(Miasto);
        KodPocztowyhasz=encryptionService.encrypt(KodPocztowy);
        Ulicahasz=encryptionService.encrypt(Ulica);
        NumerDomuhasz=encryptionService.encrypt(NumerDomu);
        NumerMieszkaniahasz=encryptionService.encrypt(NumerMieszkania);
        Krajhasz=encryptionService.encrypt(Kraj);
        Niphasz=encryptionService.encrypt(Nip);
        TelefonStacjonarnyhasz=encryptionService.encrypt(TelefonStacjonarny);
        TelefonKomorkowyhasz=encryptionService.encrypt(TelefonKomorkowy);
        System.out.println("Wyswietla zahaszowana nazweSkrocona zleceniodawcu"+ NazwaSkroconaZleceniodawcyhasz);
    }

    @SuppressWarnings("ConstantConditions")
    public void WyslijdoBazy()
    {




        Optional<Zleceniodawca> aktualny=zleceniodawcaRepository.findById(ID);

        Zleceniodawca zleceniodawca=new Zleceniodawca();

            if(aktualny.isPresent())
            {
                System.out.println("Dziala aktualny");
                Zleceniodawca istniejacy=aktualny.get();
                istniejacy.setNazwa(NazwaSkroconaZleceniodawcyhasz);
                istniejacy.setPelna(PelnaNazwaZleceniodawcyhasz);
                istniejacy.setMiasto(Miastohasz);
                istniejacy.setKod(KodPocztowyhasz);
                istniejacy.setUlica(Ulicahasz);
                istniejacy.setDom(NumerDomuhasz);
                istniejacy.setMieszkanie(NumerMieszkaniahasz);
                istniejacy.setKraj(Krajhasz);
                istniejacy.setNip(Niphasz);
                istniejacy.setStacjonarny(TelefonStacjonarnyhasz);
                istniejacy.setKomorkowy(TelefonKomorkowyhasz);
                zleceniodawcaRepository.save(istniejacy);
                dispose();
            }
            else {
                System.out.println("Numer Zleceniodawcy to " + ID);
                Sprawdzmy();
                Sprawdzmy1();
                krotkanazwa=NazwaSkroconaZleceniodawcy;
                 dluganazwa=PelnaNazwaZleceniodawcy;
                System.out.println("Sprawdz co ma nazwa krotka "+krotkanazwa);

                System.out.println("Sprawdz nazwe skrocona "+SprawdzNazweSkrocona);
                if (krotkanazwa.equals(SprawdzNazweSkrocona))
                {
                    WyswietlKomunikatoBledzie("Zleceniodawca o takiej nazwie skróconej już istnieje");
                }
                else if (dluganazwa.equals(SprawdzPelnaNazweZleceniodawcy))
                {
                    WyswietlKomunikatoBledzie("ZLeceniodawca o takiej pełnej nazwie już istnieje");
                }
                else if(!NazwaSkroconaZleceniodawcy.equals(SprawdzNazweSkrocona)||!PelnaNazwaZleceniodawcy.equals(SprawdzPelnaNazweZleceniodawcy))
                {
                    zleceniodawca.setNazwa(NazwaSkroconaZleceniodawcyhasz);
                    zleceniodawca.setPelna(PelnaNazwaZleceniodawcyhasz);
                    zleceniodawca.setMiasto(Miastohasz);
                    zleceniodawca.setKod(KodPocztowyhasz);
                    zleceniodawca.setUlica(Ulicahasz);
                    zleceniodawca.setDom(NumerDomuhasz);
                    zleceniodawca.setMieszkanie(NumerMieszkaniahasz);
                    zleceniodawca.setKraj(Krajhasz);
                    zleceniodawca.setNip(Niphasz);
                    zleceniodawca.setStacjonarny(TelefonStacjonarnyhasz);
                    zleceniodawca.setKomorkowy(TelefonKomorkowyhasz);
                    zleceniodawcaRepository.save(zleceniodawca);
                    dispose();
                }
            }
        }





    public void UzupelnijDaneEdycji(Zleceniodawca zleceniodawca)
    {
        System.out.println("Numer zleceniodawcy to "+zleceniodawca.getId());
        tNazwaSkroconaZleceniodawcy.setText(encryptionService.decrypt(zleceniodawca.getNazwa()));
        aPelnaNazwaZleceniodawcy.setText(encryptionService.decrypt(zleceniodawca.getPelna()));
        tMiasto.setText(encryptionService.decrypt(zleceniodawca.getMiasto()));
        tKodPocztowy.setText(encryptionService.decrypt(zleceniodawca.getKod()));
        tUlica.setText(encryptionService.decrypt(zleceniodawca.getUlica()));
        tNumerDomu.setText(encryptionService.decrypt(zleceniodawca.getDom()));
        tNumerMieszkania.setText(encryptionService.decrypt(zleceniodawca.getMieszkanie()));
        tKraj.setText(encryptionService.decrypt(zleceniodawca.getKraj()));
        tNip.setText(encryptionService.decrypt(zleceniodawca.getNip()));
        tTelefonStacjonarny.setText(encryptionService.decrypt(zleceniodawca.getStacjonarny()));
        tTelefonKomorkowy.setText(encryptionService.decrypt(zleceniodawca.getKomorkowy()));

        if(zleceniodawca.getId()>0)
        {

            ID=zleceniodawca.getId();
            System.out.println("Numer id zleceniodawcy" +ID);
        }
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}

