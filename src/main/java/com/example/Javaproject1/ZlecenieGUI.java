package com.example.Javaproject1;
import com.toedter.calendar.JDateChooser;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


@Component
public class ZlecenieGUI extends JFrame {



     JButton bWybierzZleceniodawce,bZapisz,bWyjdz,bCzysc,bEdytuj;
    JPanel panel1,panel2,panel3,panel4,panel5;
    JTabbedPane zakladka1;
    JLabel lZleceniodawca,lUrzadzenie,lModel,lNumerSeryjny,lDataPrzyjecia,lMarka,lOpisUsterki,lWady,lUwagiSerwisanta,lDataZakonczenia;
    LimitowanyText tZleceniadawca,tUrzadzenie,tModel,tNumerSeryjny,tMarka;
    LimitowanyTextArea aOpisUsterki,aWady,aUwagiSerwisanta;
    JScrollPane scroll;

    Glowna glowna;

    JDateChooser WybierzDate = new JDateChooser();
    JDateChooser WybierzDateZakonczenia=new JDateChooser();
    JDateChooser DodajElement=new JDateChooser();
    JTable table;
    String Zleceniodawca,Urzadzenie,Marka,Model,NumerSeryjny,OpisUsterki,ZauwazoneWadyUrzadzenia,UwagiSerwisanta,DataPrzyjeciaZlecenia,DataZakonczeniaZlecenia;
    String Zleceniodawcahasz,Urzadzeniehasz,Markahasz,Modelhasz,NumerSeryjnyhasz,OpisUsterkihasz,ZauwazoneWadyUrzadzeniahasz,UwagiSerwisantahasz;
      WybierzZleceniodawceGUI wybierzZleceniodawceGUI;
      ZlecenieRepository zlecenieRepository;

      JDialog jNoweOknoDialogowe;
      String dataPrzyjeciaElementu;
       PodsumowanieRepository podsumowanieRepository;
        EncryptionService encryptionService;
    Date wybranydzien;
int Numer1;
@Autowired
    public ZlecenieGUI( WybierzZleceniodawceGUI wybierzZleceniodawceGUI,ZlecenieRepository zlecenieRepository, PodsumowanieRepository podsumowanieRepository,Glowna glowna,EncryptionService encryptionService )
    {


        this.wybierzZleceniodawceGUI=wybierzZleceniodawceGUI;
        this.zlecenieRepository=zlecenieRepository;
        this.podsumowanieRepository=podsumowanieRepository;
        this.glowna=glowna;
        this.encryptionService=encryptionService;


        wybierzZleceniodawceGUI.setZlecenieGUI(this);
        setSize(400,200);
        setExtendedState(MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        zakladka1=new JTabbedPane();


        panel1=StworzPanel1();
        panel2=StworzPanel2();
        panel3=StworzPanel3();
        panel4=StworzPanel4();
        panel5=StworzPanel5();

        zakladka1.addTab("Przyjecie urządzenia",null,panel3);
        zakladka1.addTab("Widoczne wady urządzenia",null,panel4);
        zakladka1.addTab("Podsumowanie",null,panel5);

        add(zakladka1, BorderLayout.CENTER);
        zakladka1.addChangeListener(e -> {
            if(zakladka1.getSelectedIndex()==2) {
                panel1.add(bEdytuj);
                panel1.revalidate();
                panel1.repaint();
            }
            else {
                panel1.remove(bEdytuj);

            }
            panel1.revalidate();
            panel1.repaint();
        });



    }
    public JPanel StworzPanel1()
    {
        JPanel panel=new JPanel();
        panel.setBackground(Color.red);
        panel.setPreferredSize(new Dimension(100,100));
        add(panel,BorderLayout.WEST);

        bCzysc=new JButton();
        bEdytuj=new JButton();
        bWyjdz=new JButton();
        bZapisz=new JButton();

        StworzPrzycisk(bEdytuj,"Dodaj",100,20,panel);
        StworzPrzycisk(bZapisz,"Zapisz",100,20,panel);
        StworzPrzycisk(bCzysc,"Czyść",100,20,panel);
        StworzPrzycisk(bWyjdz,"Wyjdź",100,20,panel);


        Czysc(bCzysc);
        Wyjdz(bWyjdz);

        bZapisz.addActionListener(e -> {

            PobierzDanezZlecenia();

            Szyfruj();
        PobierzzPodsumowania();
        Sprawdz();
        });

        bEdytuj.addActionListener(e -> {
            jNoweOknoDialogowe=new JDialog();
            StworzDodawanieDoTabeli();
        });

        return panel;
    }
    public JPanel StworzPanel2()
    {
        JPanel panel=new JPanel();
        panel.setBackground(Color.blue);
        panel.setPreferredSize(new Dimension(100, 100));
        add(panel, BorderLayout.NORTH);
        return panel;
    }
    public JPanel StworzPanel3()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.ORANGE);
        panel.setLayout(null);
        lZleceniodawca=new JLabel();
        lUrzadzenie=new JLabel();
        lMarka=new JLabel();
        lModel=new JLabel();
        lNumerSeryjny=new JLabel();
        lDataPrzyjecia=new JLabel();
        lOpisUsterki=new JLabel();
        lDataZakonczenia=new JLabel();
        tZleceniadawca=new LimitowanyText(20,false);
        tUrzadzenie=new LimitowanyText(30,false);
        tMarka=new LimitowanyText(30,false);
        tModel=new LimitowanyText(40,false);
        tNumerSeryjny=new LimitowanyText(40, false);



        bWybierzZleceniodawce=new JButton();


        WysweitlNapis(lZleceniodawca,"Zleceniodawca",50,50,100,20,panel);
        WysweitlNapis(lUrzadzenie,"Urzadzenie",50,80,100,20,panel);
        WysweitlNapis(lMarka,"Marka",50,110,100,20,panel);
        WysweitlNapis(lModel,"Model",50,140,100,20,panel);
        WysweitlNapis(lNumerSeryjny,"Numer Serujny",50,170,100,20,panel);
        WysweitlNapis(lDataPrzyjecia,"Data Przyjecia",50,200,100,20,panel);
        WysweitlNapis(lOpisUsterki,"Opis usterki",50,250,100,20,panel);
        WysweitlNapis(lDataZakonczenia,"Data zakończenia",400,200,100,20,panel);

        WpiszTekst(tZleceniadawca,150,50,150,20,panel);
        WpiszTekst(tUrzadzenie,150,80,150,20,panel);
        WpiszTekst(tMarka,150,110,150,20,panel);
        WpiszTekst(tModel,150,140,150,20,panel);
        WpiszTekst(tNumerSeryjny,150,170,150,20,panel);

        StworzPrzyckiskzDanymi(bWybierzZleceniodawce,"Wybierz zleceniodawce",310,50,150,20,panel);
        bWybierzZleceniodawce.addActionListener(e -> {
            if(wybierzZleceniodawceGUI !=null)
            {
                wybierzZleceniodawceGUI.setVisible(true);
            }
        });



        aOpisUsterki=new LimitowanyTextArea(200);
        aOpisUsterki.setWrapStyleWord(true);
        aOpisUsterki.setLineWrap(true);
        aOpisUsterki.setCaretPosition(0);

        scroll=new JScrollPane(aOpisUsterki);
        scroll.setBounds(50,270,400,200);
        panel.add(scroll);



        WybierzDate.setBounds(150,200,180,20);
        panel.add(WybierzDate);
        WybierzDateZakonczenia.setBounds(500,200,100,20);
        panel.add(WybierzDateZakonczenia);
        return panel;
    }
    public JPanel StworzPanel4()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setLayout(null);
        lWady=new JLabel();
        lUwagiSerwisanta=new JLabel();



        WysweitlNapis(lWady,"Zauważone wady urządzenia",0,0,200,20,panel);
        WysweitlNapis(lUwagiSerwisanta,"UWagi serwisanta",800,0,200,20,panel);

        aWady=new LimitowanyTextArea(200);
        aWady.setWrapStyleWord(true);
        aWady.setLineWrap(true);
        aWady.setCaretPosition(0);
        scroll=new JScrollPane(aWady);
        scroll.setBounds(0,20,400,200);
        panel.add(scroll);

        aUwagiSerwisanta=new LimitowanyTextArea(200);
        aUwagiSerwisanta.setWrapStyleWord(true);
        aUwagiSerwisanta.setLineWrap(true);
        aUwagiSerwisanta.setCaretPosition(0);
        scroll=new JScrollPane(aUwagiSerwisanta);
        scroll.setBounds(800,20,400,200);
        panel.add(scroll);





        return panel;
    }
    public JPanel StworzPanel5() {


        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);

        // Domyślny model danych
        String[] NazwaKolumn = {"Data", "Element", "Kwota"};
        DefaultTableModel tabelaModel = new DefaultTableModel(NazwaKolumn, 0);
        table = new JTable(tabelaModel);
        PobierzDanezTabeli();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }


    public void StworzPrzycisk(JButton button,String nazwa,int a,int b,JPanel panel)
    {
        button.setPreferredSize(new Dimension(a,b));
        button.setText(nazwa);
        panel.add(button);
    }
    public void StworzPrzyckiskzDanymi(JButton button,String nazwa,int a,int b, int c,int d, JPanel panel)
    {
        button.setText(nazwa);
        button.setBounds(a,b,c,d);
        panel.add(button);
    }
    public void WysweitlNapis(JLabel label,String napis,int a, int b,int c, int d, JPanel panel)
    {
        label.setText(napis);
        label.setBounds(a,b,c,d);
        panel.add(label);
    }
    public void WpiszTekst(JTextField text,int a,int b,int c,int d,JPanel panel)
    {

        text.setBounds(a,b,c,d);
        panel.add(text);
    }
    public void setTZleceniodawca(String text)
    {
        tZleceniadawca.setText(text);
    }

    public void Czysc(JButton button)
    {
        button.addActionListener(e -> {
            tZleceniadawca.setText(" ");
            tUrzadzenie.setText(" ");
            tMarka.setText(" ");
            tModel.setText(" ");
            tNumerSeryjny.setText(" ");
            aOpisUsterki.setText(" ");
        });
    }
    public void Wyjdz(JButton button)
    {
        button.addActionListener(e -> dispose());
    }


    public void PobierzDanezZlecenia()
    {
        Zleceniodawca=tZleceniadawca.getText();
        Urzadzenie=tUrzadzenie.getText().trim();
        Marka=tMarka.getText().trim();
        Model=tModel.getText().trim();
        NumerSeryjny=tNumerSeryjny.getText().trim();
        OpisUsterki=aOpisUsterki.getText().trim();
        ZauwazoneWadyUrzadzenia=aWady.getText().trim();
        UwagiSerwisanta=aUwagiSerwisanta.getText().trim();
        System.out.println(Zleceniodawca);
        System.out.println(Urzadzenie);
        System.out.println(Marka);
        System.out.println(Model);
        System.out.println(NumerSeryjny);
        System.out.println(OpisUsterki);
        System.out.println(ZauwazoneWadyUrzadzenia);
        System.out.println(UwagiSerwisanta);
        PobierzDate();
        System.out.println("Data rozpoczecia to"+DataPrzyjeciaZlecenia);

       if(WybierzDateZakonczenia.getDate() !=null) {
           PobierzDateZakonczenia();
           System.out.println("SPrawdzam czy dziala w PObierzDanezZlecenia"+ DataZakonczeniaZlecenia);
       }



    }
    public void Szyfruj()
    {
        Zleceniodawcahasz=encryptionService.encrypt(Zleceniodawca);
        Urzadzeniehasz=encryptionService.encrypt(Urzadzenie);
        Markahasz=encryptionService.encrypt(Marka);
        Modelhasz=encryptionService.encrypt(Model);
        NumerSeryjnyhasz=encryptionService.encrypt(NumerSeryjny);
        OpisUsterkihasz=encryptionService.encrypt(OpisUsterki);
        ZauwazoneWadyUrzadzeniahasz=encryptionService.encrypt(ZauwazoneWadyUrzadzenia);
        UwagiSerwisantahasz=encryptionService.encrypt(UwagiSerwisanta);

    }

    public void Sprawdz()
    {
        String ZLeceniodawca1=Zleceniodawca;
        String Urzadzenie1=Urzadzenie;
        String Marka1=Marka;
        String Model1=Model;
        String NumerSeryjny1=NumerSeryjny;
        String OpisUsterki1=OpisUsterki;

        if(ZLeceniodawca1.isEmpty())
        {
            WyswietlKomunikatoBledzie("Nie wybrales zleceniodawcy");
        }
        else if (Urzadzenie1.isEmpty())
        {
        WyswietlKomunikatoBledzie("Nie wpisales co to za urzadzenie");
        }
        else if (Marka1.isEmpty())
        {
            WyswietlKomunikatoBledzie("Nie wpisales marki");
        }
        else if (Model1.isEmpty())
        {
            WyswietlKomunikatoBledzie("Nie wpisales modelu urządzenia");
        }
        else if (NumerSeryjny1.isEmpty())
        {
         WyswietlKomunikatoBledzie("Nie wpisałeś numeru seryjnego");
        }
        else if (wybranydzien==null)
        {
            WyswietlKomunikatoBledzie("Nie wybrałeś daty, kiedy przyjęto zlecenie do realizacji");
        }
        else if (OpisUsterki1.isEmpty())
        {
            WyswietlKomunikatoBledzie("Nie wpisałeś usterki odnośnie urządzenia");
        }
        else if (!Zleceniodawca.isEmpty()||!Urzadzenie.isEmpty()||!Marka.isEmpty()||!Model.isEmpty()||!NumerSeryjny.isEmpty()||!DataPrzyjeciaZlecenia.isEmpty()||!OpisUsterki.isEmpty())
        {
            WyslijdoBazyZlecenie();
        }
    }
    public void WyslijdoBazyZlecenie()
    {



        Zlecenie zlecenie=new Zlecenie();

        Optional<Zlecenie> aktualny=zlecenieRepository.findByNumer(glowna.getNumer());
        if(aktualny.isPresent())
        {
            System.out.println("Dodaje nowego");
            Zlecenie istniejacy=aktualny.get();
            istniejacy.setZleceniodawca(Zleceniodawcahasz);
            istniejacy.setPrzyjecie(DataPrzyjeciaZlecenia);
            istniejacy.setUrzadzenie(Urzadzeniehasz);
            istniejacy.setMarka(Markahasz);
            istniejacy.setModel(Modelhasz);
            istniejacy.setSeryjny(NumerSeryjnyhasz);
            istniejacy.setUsterka(OpisUsterkihasz);
            istniejacy.setWady(ZauwazoneWadyUrzadzeniahasz);
            istniejacy.setUwagi(UwagiSerwisantahasz);
            istniejacy.setZakonczenie(DataZakonczeniaZlecenia);
            zlecenieRepository.save(istniejacy);
        }
        else {
            System.out.println("edytuje aktualnego");
            zlecenie.setNumer(glowna.getNumer());
            zlecenie.setZleceniodawca(Zleceniodawcahasz);
            zlecenie.setPrzyjecie(DataPrzyjeciaZlecenia);
            zlecenie.setUrzadzenie(Urzadzeniehasz);
            zlecenie.setMarka(Markahasz);
            zlecenie.setModel(Modelhasz);
            zlecenie.setSeryjny(NumerSeryjnyhasz);
            zlecenie.setUsterka(OpisUsterkihasz);
            zlecenie.setWady(ZauwazoneWadyUrzadzeniahasz);
            zlecenie.setUwagi(UwagiSerwisantahasz);
            zlecenie.setZakonczenie(DataZakonczeniaZlecenia);
            System.out.println("Data ktora wysylana jest do bazy danych to "+zlecenie.getZakonczenie());
            zlecenieRepository.save(zlecenie);
        }
    }


   public void PobierzDate()
   {

            wybranydzien = WybierzDate.getDate();
           if(wybranydzien!=null) {
               SimpleDateFormat FormatDaty = new SimpleDateFormat("yyyy-MM-dd");
               DataPrzyjeciaZlecenia = FormatDaty.format(wybranydzien);
           }
   }
    public void PobierzDateZakonczenia()
    {
        Date wybranydzien=WybierzDateZakonczenia.getDate();
        SimpleDateFormat FormatDaty=new SimpleDateFormat("yyyy-MM-dd");
        DataZakonczeniaZlecenia=FormatDaty.format(wybranydzien);
        System.out.println("Data zakonczenia to"+ DataZakonczeniaZlecenia);
    }
    public void StworzDodawanieDoTabeli()
    {


        jNoweOknoDialogowe.setSize(400, 200);
        jNoweOknoDialogowe.setLayout(null);
        jNoweOknoDialogowe.setVisible(true);
        jNoweOknoDialogowe.setLocationRelativeTo(null);

        JButton bWyjdz,bZapisz;
        JLabel lData,lElement,lKwota;
        JTextField tElement,tKwota;
        lData=new JLabel();
        lElement=new JLabel();
        lKwota=new JLabel();
        tKwota=new JTextField();
        tElement=new JTextField();

        lData.setText("Data");
        lData.setBounds(10,10,100,20);

        lElement.setText("Wykonana czynność");
        lElement.setBounds(10,40,150,20);

        lKwota.setText("Kwota");
        lKwota.setBounds(10,70,100,20);

        tElement.setBounds(130,40,100,20);
        tKwota.setBounds(130,70,100,20);

        bWyjdz=new JButton();
        bWyjdz.setText("Wyjdz");
        bWyjdz.setBounds(140,110,100,20);

        bZapisz=new JButton();
        bZapisz.setText("Zapisz");
        bZapisz.setBounds(30,110,100,20);
        DodajElement.setBounds(130,10,100,20);


        jNoweOknoDialogowe.add(lData);
        jNoweOknoDialogowe.add(lElement);
        jNoweOknoDialogowe.add(lKwota);
        jNoweOknoDialogowe.add(tKwota);
        jNoweOknoDialogowe.add(tElement);
        jNoweOknoDialogowe.add(DodajElement);
        jNoweOknoDialogowe.add(bZapisz);
        jNoweOknoDialogowe.add(bWyjdz);

        bWyjdz.addActionListener(e -> jNoweOknoDialogowe.dispose());

        bZapisz.addActionListener(e -> {// oprogramowanie przycisku zapisz

        String element=tElement.getText();
        String kwota=tKwota.getText();

        Date wybranyDzien=DodajElement.getDate();
        SimpleDateFormat FormatDaty=new SimpleDateFormat("yyyy-MM-dd");
        String SformatowanaData=FormatDaty.format(wybranyDzien);
            System.out.println(SformatowanaData);
        dataPrzyjeciaElementu=SformatowanaData;

            DefaultTableModel modeltabeli=(DefaultTableModel)table.getModel();
        modeltabeli.addRow(new Object[]{dataPrzyjeciaElementu,element,kwota});
        modeltabeli.fireTableDataChanged();
        jNoweOknoDialogowe.dispose();
        });


    }
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void PobierzzPodsumowania()
    {
        DefaultTableModel model=(DefaultTableModel) table.getModel();
        int wiersz=model.getRowCount();

        List<Podsumowanie> podsumowanieList=new ArrayList<>();

        for(int i=0;i<wiersz;i++)
        {

            Podsumowanie podsumowanie=new Podsumowanie();
            String data=(String) model.getValueAt(i,0);
            String wykonany=(String) model.getValueAt(i,1);
            String kwotaString = model.getValueAt(i, 2).toString();
            kwotaString=kwotaString.replace(",",  "."); // zamiana przecinka na kropke
            double kwota=Double.parseDouble(kwotaString);


            String ZaszyfrowaneWykonane=encryptionService.encrypt(wykonany);

            System.out.println("Data "+data+" "+"wykonanie "+wykonany+" "+"kwota "+kwota);
            podsumowanie.setNumer(glowna.getNumer());
            podsumowanie.setDataa(data);
            podsumowanie.setWykonane(ZaszyfrowaneWykonane);
            podsumowanie.setKwota(kwota);
            podsumowanieList.add(podsumowanie);


        }



        for(int i=0;i<podsumowanieList.size();i++)
        {
            Podsumowanie podsumowanie = podsumowanieList.get(i);
            System.out.println("Wysiwetlam liste");
            System.out.println("Data: " + podsumowanie.getDataa() + ", Wykonanie: " + podsumowanie.getWykonane() + ", Kwota: " + podsumowanie.getKwota());
        }

        podsumowanieRepository.saveAll(podsumowanieList);

    }
    public void UzupelnijDaneEdycji(Zlecenie zlecenie)
    {
       tZleceniadawca.setText(encryptionService.decrypt(zlecenie.getZleceniodawca()));
       tUrzadzenie.setText(encryptionService.decrypt(zlecenie.getUrzadzenie()));
       tMarka.setText(encryptionService.decrypt(zlecenie.getMarka()));
       tModel.setText(encryptionService.decrypt(zlecenie.getModel()));
       tNumerSeryjny.setText(encryptionService.decrypt(zlecenie.getSeryjny()));
       aOpisUsterki.setText(encryptionService.decrypt(zlecenie.getUsterka()));
       aWady.setText(encryptionService.decrypt(zlecenie.getWady()));
       aUwagiSerwisanta.setText(encryptionService.decrypt(zlecenie.getUwagi()));

        SimpleDateFormat formaDaty = new SimpleDateFormat("yyyy-MM-dd");
        String dataPrzyejcia = zlecenie.getPrzyjecie();
        String dataZakonczenia=zlecenie.getZakonczenie();

        if (dataPrzyejcia != null && !dataPrzyejcia.isEmpty()) {
            try {
                Date dataPrzyjecia1 = formaDaty.parse(dataPrzyejcia);
                WybierzDate.setDate(dataPrzyjecia1);
            } catch (ParseException e) {
                e.printStackTrace(); //
            }
        }
        if(dataZakonczenia!=null&&!dataZakonczenia.isEmpty())
        {
            try {
                Date dataZakonczenia1 = formaDaty.parse(dataZakonczenia);
                WybierzDateZakonczenia.setDate(dataZakonczenia1);
            } catch (ParseException e) {
                e.printStackTrace(); //
            }
        }


    }

    public void setNumer1(int numer1) {
        Numer1 = numer1;
    }


    public void PobierzDanezTabeli() {
        List<Podsumowanie> dane = podsumowanieRepository.findAllByNumer(glowna.getNumer());

        DefaultTableModel tabelaModel = (DefaultTableModel) table.getModel();

        tabelaModel.setRowCount(0);

        for (Podsumowanie podsumowanie : dane) {
            Object[] row = {podsumowanie.getDataa(),encryptionService.decrypt(podsumowanie.getWykonane()), podsumowanie.getKwota()};
            tabelaModel.addRow(row);
        }
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


}
