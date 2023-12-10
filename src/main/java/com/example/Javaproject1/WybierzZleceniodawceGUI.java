package com.example.Javaproject1;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


@Component
public class WybierzZleceniodawceGUI extends JFrame  {


    @Autowired
     ZleceniodawcaRepository zleceniodawcaRepository;
     ZlecenieGUI zlecenieGUI;
      JTable tTabela;

    JButton bWyjdz,bNowyZleceniodawca,bEdytuj,bOdswiez;
     JPanel panel1,panel2,panel3;
    int WybranyWiersz;
    EncryptionService encryptionService;

    private final ZleceniodawcaGUI zleceniodawcaGUI;
int ID;
    public WybierzZleceniodawceGUI(ZleceniodawcaRepository zleceniodawcaRepository, ZleceniodawcaGUI zleceniodawcaGUI,EncryptionService encryptionService)
    {
        this.zleceniodawcaRepository=zleceniodawcaRepository;
        this.zleceniodawcaGUI=zleceniodawcaGUI;
        this.encryptionService=encryptionService;
        setSize(400,200);
        setExtendedState(MAXIMIZED_BOTH);
        panel1=StworzPanel1();
        panel2=StworzPanel2();
        panel3=StworzPanel3();
        revalidate();

        add(panel3, BorderLayout.CENTER);

        WybierzZleceniodawce();

    }

    @SuppressWarnings("ConstantConditions")
    public JPanel StworzPanel1()
    {
        JPanel panel=new JPanel();
        panel.setBackground(Color.red);
        panel.setPreferredSize(new Dimension(100,100));
        add(panel,BorderLayout.WEST);


        bNowyZleceniodawca=new JButton();
        StworzPrzycisk(bNowyZleceniodawca,"nowy",100,20,panel);
        bNowyZleceniodawca.addActionListener(e -> {
            Wyczysc();
            zleceniodawcaGUI.setID(0);
            if (zleceniodawcaGUI != null) {
                zleceniodawcaGUI.setVisible(true);
            }
        });
        bEdytuj=new JButton();
        StworzPrzycisk(bEdytuj,"edytuj",100,20,panel);
        bEdytuj.addActionListener(e -> {

            Wyczysc();
            Edytuj();

        });

        bOdswiez=new JButton();
        StworzPrzycisk(bOdswiez,"odswiez",100,20,panel);
        bOdswiez.addActionListener(e -> WypelnijTabele());
        bWyjdz=new JButton();
        StworzPrzycisk(bWyjdz,"wyjdz",100,20,panel);
        bWyjdz.addActionListener(e ->
            dispose()
        );








        return panel;
    }
    public  JPanel StworzPanel2()
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
        panel.setLayout(new BorderLayout());



        tTabela = new JTable();
        JScrollPane przesun = new JScrollPane(tTabela);
        panel.add(przesun, BorderLayout.CENTER); // Dodaj tTabela do panelu StworzPanel3.
        WypelnijTabele();

        return panel;
    }


    public void WypelnijTabele()
    {

        List<Zleceniodawca> zleceniodawcy=zleceniodawcaRepository.findAll();
        Object[][] dane=new Object[zleceniodawcy.size()][12];

        for(int i=0;i<zleceniodawcy.size();i++)
        {

                Zleceniodawca zleceniodawca=zleceniodawcy.get(i);
                dane[i][0]=zleceniodawca.getId();
                dane[i][1]=encryptionService.decrypt(zleceniodawca.getNazwa());
                dane[i][2]=encryptionService.decrypt(zleceniodawca.getPelna());
                dane[i][3]=encryptionService.decrypt(zleceniodawca.getMiasto());
                dane[i][4]=encryptionService.decrypt(zleceniodawca.getKod());
                dane[i][5]=encryptionService.decrypt(zleceniodawca.getUlica());
                dane[i][6]=encryptionService.decrypt(zleceniodawca.getDom());
                dane[i][7]=encryptionService.decrypt(zleceniodawca.getMieszkanie());
                dane[i][8]=encryptionService.decrypt(zleceniodawca.getKraj());
                dane[i][9]=encryptionService.decrypt(zleceniodawca.getNip());
                dane[i][10]=encryptionService.decrypt(zleceniodawca.getStacjonarny());
                dane[i][11]=encryptionService.decrypt(zleceniodawca.getKomorkowy());



        }
        String[] NazwyKolumn={
                KontrolerJezyka.resourceBundle.getString("ID"),
                KontrolerJezyka.resourceBundle.getString("skroconaNazwaZleceniodawcy"),
                KontrolerJezyka.resourceBundle.getString("pelnaNazwaZleceniodawcy"),
                KontrolerJezyka.resourceBundle.getString("miasto"),
                KontrolerJezyka.resourceBundle.getString("kodPocztowy"),
                KontrolerJezyka.resourceBundle.getString("ulica"),
                KontrolerJezyka.resourceBundle.getString("numerDomu"),
                KontrolerJezyka.resourceBundle.getString("numerMieszkania"),
                KontrolerJezyka.resourceBundle.getString("kraj"),
                KontrolerJezyka.resourceBundle.getString("NIP"),
                KontrolerJezyka.resourceBundle.getString("telefonStacjonarny"),
                KontrolerJezyka.resourceBundle.getString("telefonKomorkowy")
        };
        DefaultTableModel model=new DefaultTableModel(dane,NazwyKolumn);
        tTabela.setModel(model);


    }
    public void StworzPrzycisk(JButton button,String nazwa,int a,int b,JPanel panel)
    {
        nazwa=KontrolerJezyka.resourceBundle.getString(nazwa);
        button.setPreferredSize(new Dimension(a,b));
        button.setText(nazwa);
        panel.add(button);
    }

    public void WybierzZleceniodawce()
    {
        tTabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Wykonuj tylko po podwójnym kliknięciu
                     WybranyWiersz = tTabela.getSelectedRow();
                    if (WybranyWiersz >= 0)
                    {
                        Object wybrany = tTabela.getValueAt(WybranyWiersz, 1);
                        zlecenieGUI.setTZleceniodawca(wybrany.toString());
                        dispose();
                    }
                }

            }

        });

        tTabela.setDefaultEditor(Object.class, null);

    }
    public void setZlecenieGUI(ZlecenieGUI zlecenieGUI)
    {
        this.zlecenieGUI=zlecenieGUI;
    }

   public void Edytuj() {
        int WybranyWiersz=tTabela.getSelectedRow();
        if(WybranyWiersz>=0)
        {
            Zleceniodawca edytowanyZleceniodawca = zleceniodawcaRepository.findById((Integer) tTabela.getValueAt(WybranyWiersz, 0)).orElse(null);
            if(edytowanyZleceniodawca!=null)
            {
                ID= edytowanyZleceniodawca.getId();
                System.out.println("Edytowany ZLeceniodawca "+ID);
                zleceniodawcaGUI.UzupelnijDaneEdycji(edytowanyZleceniodawca);
                zleceniodawcaGUI.setVisible(true);
                zleceniodawcaGUI.setID(ID);
            }
        }
   }



    public void Wyczysc()
    {
        zleceniodawcaGUI.tNazwaSkroconaZleceniodawcy.setText("");
        zleceniodawcaGUI.tMiasto.setText("");
        zleceniodawcaGUI.tKodPocztowy.setText("");
        zleceniodawcaGUI.tUlica.setText("");
        zleceniodawcaGUI.tNumerDomu.setText("");
        zleceniodawcaGUI.tNumerMieszkania.setText("");
        zleceniodawcaGUI.tNip.setText("");
        zleceniodawcaGUI.tKraj.setText("");
        zleceniodawcaGUI.tTelefonStacjonarny.setText("");
        zleceniodawcaGUI.tTelefonKomorkowy.setText("");
        zleceniodawcaGUI.aPelnaNazwaZleceniodawcy.setText("");

    }


}
