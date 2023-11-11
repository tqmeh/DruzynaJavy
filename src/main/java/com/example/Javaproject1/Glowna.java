package com.example.Javaproject1;

import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


@Component
public class Glowna extends JFrame {
    JPanel panel1,panel2,panel3;
    JButton bNowy,bSprawdz,bOdswiez,bEdytuj;
     NumerRepository numerRepository;

    WybierzZleceniodawceGUI wybierzZleceniodawceGUI;
    ZlecenieRepository zlecenieRepository;
    PodsumowanieRepository podsumowanieRepository;

    JTable tTabela;
     EncryptionService encryptionService;

    int Numer;

    int ID;
    public Glowna(NumerRepository numerRepository,WybierzZleceniodawceGUI wybierzZleceniodawceGUI, ZlecenieRepository zlecenieRepository,PodsumowanieRepository podsumowanieRepository,EncryptionService encryptionService)
    {

        this.numerRepository=numerRepository;
        this.wybierzZleceniodawceGUI=wybierzZleceniodawceGUI;
        this.zlecenieRepository=zlecenieRepository;
        this.podsumowanieRepository=podsumowanieRepository;
        this.encryptionService=encryptionService;

        setSize (400,200);
        setTitle("Logowanie");
        setExtendedState(MAXIMIZED_BOTH);

        panel1=StworzPanel1();
        panel2=StworzPanel2();
        panel3=StworzPanel3();









    }
    public JPanel StworzPanel1()
    {
        JPanel panel=new JPanel();
        panel.setBackground(Color.red);
        panel.setPreferredSize(new Dimension(100,100));
        add(panel,BorderLayout.WEST);

        bSprawdz=new JButton();
        bNowy=new JButton();
        bOdswiez=new JButton();
        bEdytuj=new JButton();

        StworzPrzycisk(bNowy,"Nowe Zlecenie",100,20,panel);
        bNowy.addActionListener(e -> {
          Numer=SprawdzNumer();
            NumerZlecenia();
            ZlecenieGUI zlecenie=new ZlecenieGUI(wybierzZleceniodawceGUI,zlecenieRepository,podsumowanieRepository,this,encryptionService);
            zlecenie.setVisible(true);
            zlecenie.setNumer1(0);

        });



        StworzPrzycisk(bSprawdz,"Sprawdz",100,20,panel);


        StworzPrzycisk(bOdswiez,"Odświez",100,20,panel);
        bOdswiez.addActionListener(e -> UtworzTabele());

        StworzPrzycisk(bEdytuj,"Edytuj",100,20,panel);
        bEdytuj.addActionListener(e ->
            EdytujiWyczysc()


        );
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
        JPanel panel=new JPanel();
        panel.setBackground(Color.orange);
        panel.setLayout(new BorderLayout());
        add(panel,BorderLayout.CENTER);

        tTabela=new JTable();
        JScrollPane przesun = new JScrollPane(tTabela);
        panel.add(przesun, BorderLayout.CENTER);
        UtworzTabele();
        return panel;
    }

    public void StworzPrzycisk(JButton button,String nazwa,int a,int b,JPanel panel)
    {
        button.setPreferredSize(new Dimension(a,b));
        button.setText(nazwa);
        panel.add(button);
    }
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public int SprawdzNumer()
    {


        List<Numer> ListaNumerow=numerRepository.findAll();

        Numer=0;
        for(int i=0;i<ListaNumerow.size();i++)
        {
            int sprawdz=ListaNumerow.get(i).getNumer();
            if(Numer>sprawdz)
            {
                Numer=sprawdz;
            }
            else
            {
                Numer++;
            }

        }
        System.out.println("Najwiekszy numer to "+Numer);
        return Numer;
    }
    public int getNumer() {
        System.out.println("Numer to "+Numer);
        return Numer;
    }

    public void NumerZlecenia()
    {    Numer numer=new Numer();
        System.out.println("Zapisany numer to "+Numer);
        numer.setNumer(Numer);
        numerRepository.save(numer);
    }

    public void UtworzTabele()
    {
        List<Zlecenie> zlecenie=zlecenieRepository.findAll();
        Object[][] dane=new Object[zlecenie.size()][11];
        for(int i=0;i<zlecenie.size();i++)
        {
            Zlecenie zlecenie1=zlecenie.get(i);
            dane[i][0]=zlecenie1.getNumer();
            dane[i][1]=encryptionService.decrypt(zlecenie1.getZleceniodawca());
            dane[i][2]=zlecenie1.getPrzyjecie();
            dane[i][3]=encryptionService.decrypt(zlecenie1.getUrzadzenie());
            dane[i][4]=encryptionService.decrypt(zlecenie1.getMarka());
            dane[i][5]=encryptionService.decrypt(zlecenie1.getModel());
            dane[i][6]=encryptionService.decrypt(zlecenie1.getSeryjny());
            dane[i][7]=encryptionService.decrypt(zlecenie1.getUsterka());
            dane[i][8]=encryptionService.decrypt(zlecenie1.getWady());
            dane[i][9]=encryptionService.decrypt(zlecenie1.getUwagi());
            dane[i][10]=zlecenie1.getZakonczenie();
        }
        String[] NazwyKolumn={"Numer","Zleceniodawca","Przyjeto","Urzadzenie","Marka","Model","Seryjny","Usterka","Wady","Uwagi","Zakonczono"};
        DefaultTableModel model=new DefaultTableModel(dane,NazwyKolumn);
        tTabela.setModel(model);

    }
    public void EdytujiWyczysc()
    {
        ZlecenieGUI zlecenieGUI=new ZlecenieGUI(wybierzZleceniodawceGUI,zlecenieRepository,podsumowanieRepository,this,encryptionService);
        zlecenieGUI.tZleceniadawca.setText(" ");
        zlecenieGUI.tUrzadzenie.setText(" ");
        zlecenieGUI.tModel.setText(" ");
        zlecenieGUI.tNumerSeryjny.setText(" ");
        zlecenieGUI.tMarka.setText(" ");
        zlecenieGUI.aOpisUsterki.setText(" ");
        zlecenieGUI.aWady.setText(" ");
        zlecenieGUI.aUwagiSerwisanta.setText(" ");

        int WybranyWiersz=tTabela.getSelectedRow();
        System.out.println("Wybrany wiersz to "+WybranyWiersz);
        Zlecenie edytowaneZlecenie1 = zlecenieRepository.findByNumer((Integer) tTabela.getValueAt(WybranyWiersz, 0)).orElse(null);

        if(edytowaneZlecenie1!=null)
        {
            ID=edytowaneZlecenie1.getNumer();
            System.out.println(ID);
            zlecenieGUI.UzupelnijDaneEdycji(edytowaneZlecenie1);
            SwingUtilities.invokeLater(zlecenieGUI::PobierzDanezTabeli);
            zlecenieGUI.setVisible(true);
            zlecenieGUI.setNumer1(ID);
        }

    }





    }

