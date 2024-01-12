package com.example.Javaproject1;

import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@Component
public class Glowna extends JFrame {
    JPanel panel1, panel2, panel3;
    JButton bNowy, bSprawdz, bOdswiez, bEdytuj, bFaktura, bRaport;
    NumerRepository numerRepository;

    WybierzZleceniodawceGUI wybierzZleceniodawceGUI;
    ZlecenieRepository zlecenieRepository;
    private ZleceniodawcaRepository zleceniodawcaRepository;
    PodsumowanieRepository podsumowanieRepository;

    JTable tTabela;
    EncryptionService encryptionService;
    private GeneratorRaportow generatorRaportow;
    private GeneratorPdf generatorPdf;

    int Numer;

    int ID;

    public Glowna(NumerRepository numerRepository, WybierzZleceniodawceGUI wybierzZleceniodawceGUI, ZlecenieRepository zlecenieRepository, ZleceniodawcaRepository zleceniodawcaRepository, PodsumowanieRepository podsumowanieRepository, EncryptionService encryptionService, GeneratorRaportow generatorRaportow, GeneratorPdf generatorPdf) {

        this.numerRepository = numerRepository;
        this.wybierzZleceniodawceGUI = wybierzZleceniodawceGUI;
        this.zlecenieRepository = zlecenieRepository;
        this.zleceniodawcaRepository = zleceniodawcaRepository;
        this.podsumowanieRepository = podsumowanieRepository;
        this.encryptionService = encryptionService;
        this.generatorRaportow = generatorRaportow;
        this.generatorPdf = generatorPdf;

        setSize(400, 200);
        revalidate();
        setTitle(KontrolerJezyka.resourceBundle.getString("tytulGlownej"));
        setExtendedState(MAXIMIZED_BOTH);

        panel1 = StworzPanel1();
        panel2 = StworzPanel2();
        panel3 = StworzPanel3();
    }

    public JPanel StworzPanel1() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        panel.setPreferredSize(new Dimension(100, 100));
        add(panel, BorderLayout.WEST);

        bSprawdz = new JButton();
        bNowy = new JButton();
        bOdswiez = new JButton();
        bEdytuj = new JButton();
        bFaktura = new JButton();
        bRaport = new JButton();

        StworzPrzycisk(bNowy, "noweZlecenie", 100, 20, panel);
        bNowy.addActionListener(e -> {
            Numer = SprawdzNumer();
            NumerZlecenia();
            ZlecenieGUI zlecenie = new ZlecenieGUI(wybierzZleceniodawceGUI, zlecenieRepository, podsumowanieRepository, this, encryptionService);
            zlecenie.setVisible(true);
            zlecenie.setNumer1(0);
        });


        StworzPrzycisk(bSprawdz, "sprawdz", 100, 20, panel);


        StworzPrzycisk(bOdswiez, "odswiez", 100, 20, panel);
        bOdswiez.addActionListener(e -> UtworzTabele());

        StworzPrzycisk(bEdytuj, "edytuj", 100, 20, panel);
        bEdytuj.addActionListener(e -> EdytujiWyczysc());


        StworzPrzycisk(bFaktura, "faktura", 100, 20, panel);
        bFaktura.addActionListener(e -> generujFakture());
        StworzPrzycisk(bRaport, "raport", 100, 20, panel);
        bRaport.addActionListener(e -> generujRaport());
        return panel;

    }

    public JPanel StworzPanel2() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.blue);
        panel.setPreferredSize(new Dimension(100, 100));
        add(panel, BorderLayout.NORTH);
        return panel;
    }

    public JPanel StworzPanel3() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.orange);
        panel.setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        tTabela = new JTable();
        JScrollPane przesun = new JScrollPane(tTabela);
        panel.add(przesun, BorderLayout.CENTER);
        UtworzTabele();
        return panel;
    }

    public void StworzPrzycisk(JButton button, String nazwa, int a, int b, JPanel panel) {
        nazwa = KontrolerJezyka.resourceBundle.getString(nazwa);
        button.setPreferredSize(new Dimension(a, b));
        button.setText(nazwa);
        panel.add(button);
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public int SprawdzNumer() {


        List<Numer> ListaNumerow = numerRepository.findAll();

        Numer = 0;
        for (int i = 0; i < ListaNumerow.size(); i++) {
            int sprawdz = ListaNumerow.get(i).getNumer();
            if (Numer > sprawdz) {
                Numer = sprawdz;
            } else {
                Numer++;
            }

        }
        System.out.println("Najwiekszy numer to " + Numer);
        return Numer;
    }

    public int getNumer() {
        System.out.println("Numer to " + Numer);
        return Numer;
    }

    public void NumerZlecenia() {
        Numer numer = new Numer();
        System.out.println("Zapisany numer to " + Numer);
        numer.setNumer(Numer);
        numerRepository.save(numer);
    }

    public void UtworzTabele() {
        List<Zlecenie> zlecenie = zlecenieRepository.findAll();
        Object[][] dane = new Object[zlecenie.size()][12];
        for (int i = 0; i < zlecenie.size(); i++) {
            Zlecenie zlecenie1 = zlecenie.get(i);
            dane[i][0] = zlecenie1.getNumer();
            dane[i][1] = encryptionService.decrypt(zlecenie1.getZleceniodawca());
            dane[i][2] = zlecenie1.getPrzyjecie();
            dane[i][3] = encryptionService.decrypt(zlecenie1.getUrzadzenie());
            dane[i][4] = encryptionService.decrypt(zlecenie1.getMarka());
            dane[i][5] = encryptionService.decrypt(zlecenie1.getModel());
            dane[i][6] = encryptionService.decrypt(zlecenie1.getSeryjny());
            dane[i][7] = encryptionService.decrypt(zlecenie1.getUsterka());
            dane[i][8] = encryptionService.decrypt(zlecenie1.getWady());
            dane[i][9] = encryptionService.decrypt(zlecenie1.getUwagi());
            dane[i][10] = zlecenie1.getZakonczenie();
            dane[i][11] = zlecenie1.getStatus();
        }
        String[] NazwyKolumn = {
                KontrolerJezyka.resourceBundle.getString("numer"),
                KontrolerJezyka.resourceBundle.getString("zleceniodawca"),
                KontrolerJezyka.resourceBundle.getString("przyjeto"),
                KontrolerJezyka.resourceBundle.getString("urzadzenie"),
                KontrolerJezyka.resourceBundle.getString("marka"),
                KontrolerJezyka.resourceBundle.getString("model"),
                KontrolerJezyka.resourceBundle.getString("seryjny"),
                KontrolerJezyka.resourceBundle.getString("usterka"),
                KontrolerJezyka.resourceBundle.getString("wady"),
                KontrolerJezyka.resourceBundle.getString("uwagi"),
                KontrolerJezyka.resourceBundle.getString("zakonczono"),
                KontrolerJezyka.resourceBundle.getString("status")
        };
        DefaultTableModel model = new DefaultTableModel(dane, NazwyKolumn);
        tTabela.setModel(model);

    }

    public void EdytujiWyczysc() {
        ZlecenieGUI zlecenieGUI = new ZlecenieGUI(wybierzZleceniodawceGUI, zlecenieRepository, podsumowanieRepository, this, encryptionService);
        zlecenieGUI.tZleceniadawca.setText(" ");
        zlecenieGUI.tUrzadzenie.setText(" ");
        zlecenieGUI.tModel.setText(" ");
        zlecenieGUI.tNumerSeryjny.setText(" ");
        zlecenieGUI.tMarka.setText(" ");
        zlecenieGUI.aOpisUsterki.setText(" ");
        zlecenieGUI.aWady.setText(" ");
        zlecenieGUI.aUwagiSerwisanta.setText(" ");

        Zlecenie edytowaneZlecenie1 = pobierzWybraneZlecenie();

        if (edytowaneZlecenie1 != null) {
            ID = edytowaneZlecenie1.getNumer();
            System.out.println(ID);
            zlecenieGUI.UzupelnijDaneEdycji(edytowaneZlecenie1);
            SwingUtilities.invokeLater(zlecenieGUI::PobierzDanezTabeli);
            zlecenieGUI.setVisible(true);
            zlecenieGUI.setNumer1(ID);
        }
    }

    private Zlecenie pobierzWybraneZlecenie() {
        int wybranyWiersz = tTabela.getSelectedRow();
        if (wybranyWiersz == -1) {
            return null;
        }
        return zlecenieRepository.findByNumer((Integer) tTabela.getValueAt(wybranyWiersz, 0))
                .orElse(null);
    }

    private String zleceniodawcyDoWyswietlenia() {
        List<Zleceniodawca> zleceniodawcy = zleceniodawcaRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Zleceniodawca zleceniodawca : zleceniodawcy) {
            stringBuilder.append(String.format("Id: %d - %s\n",zleceniodawca.getId(),
                    encryptionService.decrypt(zleceniodawca.getNazwa())));
        }
        return stringBuilder.toString();
    }

    private void generujFakture() {
        try{
            String idZleceniodawcy = JOptionPane.showInputDialog("Podaj ID zleceniodawcy\n" + zleceniodawcyDoWyswietlenia());
            int id = Integer.parseInt(idZleceniodawcy);
        Zleceniodawca zleceniodawca = zleceniodawcaRepository.findById(id).orElseThrow();
        File plik = zapytajOLokalizacjePliku();
        RaportPdf faktura = generatorRaportow.generujFakture(zleceniodawca);
        generatorPdf.zapiszDoPliku(faktura, plik);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Należy podać liczbę!");
        }catch (NoSuchElementException e){
            JOptionPane.showMessageDialog(null,"Nie znaleziono zleceniodawcy!");
        }
    }

    private void generujRaport() {
        File plik = zapytajOLokalizacjePliku();
        RaportPdf raport = generatorRaportow.generujRaport();
        generatorPdf.zapiszDoPliku(raport, plik);
    }

    private File zapytajOLokalizacjePliku() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            String errorMessage = "Operacja anulowana przez użytkownika lub niepoprawna ścieżka.";
            JOptionPane.showMessageDialog(null, errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        return fileChooser.getSelectedFile();
    }

}

