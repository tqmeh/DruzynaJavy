package com.example.Javaproject1;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.util.*;

@Component
public class GeneratorRaportow {
    private PodsumowanieRepository podsumowanieRepository;
    private ZlecenieRepository zlecenieRepository;
    private ZleceniodawcaRepository zleceniodawcaRepository;
    private EncryptionService encryptionService;

    public GeneratorRaportow(PodsumowanieRepository podsumowanieRepository, ZlecenieRepository zlecenieRepository, ZleceniodawcaRepository zleceniodawcaRepository, EncryptionService encryptionService) {
        this.podsumowanieRepository = podsumowanieRepository;
        this.zlecenieRepository = zlecenieRepository;
        this.zleceniodawcaRepository = zleceniodawcaRepository;
        this.encryptionService = encryptionService;
    }

    public RaportPdf generujFakture(Zleceniodawca zleceniodawca){
        RaportPdf raportPdf = new RaportPdf("Faktura");
        List<Zlecenie> zleceniaWszystkie = zlecenieRepository.findAll();
        for (Zlecenie zlecenie : zleceniaWszystkie) {
            String zleceniodawcaWZleceniu = encryptionService.decrypt(zlecenie.getZleceniodawca());
            String nazwaWZleceniodawcy = encryptionService.decrypt(zleceniodawca.getNazwa());
            System.out.printf("zleceniodawca w zleceniu: %s zleceniodawca nazwa: %s\n "
                    , zleceniodawcaWZleceniu,nazwaWZleceniodawcy);
            if (!zleceniodawcaWZleceniu.equals(nazwaWZleceniodawcy)) {
                System.out.println("Nie pasował");
               continue;
            }else{
                System.out.println("Pasował");
            }
            System.out.println("Znalezione zlecenie: " + zlecenie);
            Optional<Podsumowanie> pasujacePodsumowanie = podsumowanieRepository.findByNumer(zlecenie.getNumer());
            String kwota = "brak";
            String wykonane = "brak";
            if (pasujacePodsumowanie.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null, "Brakuje podsumowania dla zlecenia nr: " + zlecenie.getNumer());
            } else {
                kwota = pasujacePodsumowanie.get().getKwota() + "";
                wykonane = encryptionService.decrypt(pasujacePodsumowanie.get().getWykonane());
            }
            raportPdf.dodajRzad(List.of(
                    zlecenie.getNumer()+"",
               wykonane,
                    kwota
            ));

        }
        raportPdf.setNaglowki(List.of(
                "numer", "wykonane", "kwota"));
        raportPdf.setOpis(zleceniodawca.daneZleceniodawcy(encryptionService));
        return raportPdf;
    }


    public  RaportPdf generujRaport(){
        List<Podsumowanie> podsumowania = podsumowanieRepository.findAll();
        RaportPdf raportPdf = new RaportPdf("Raport");
        for (Podsumowanie podsumowanie : podsumowania) {
            Optional<Zlecenie> pasujaceZlecenieOptional = zlecenieRepository.findByNumer(podsumowanie.getNumer());
            if (pasujaceZlecenieOptional.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,"Brakuje zlecenia dla podsumowania nr: " + podsumowanie.getNumer());
                continue;
            }
            Zlecenie zlecenie = pasujaceZlecenieOptional.get();
            podsumowanie.setWykonane(encryptionService.decrypt(podsumowanie.getWykonane() ));
            zlecenie.setZleceniodawca(encryptionService.decrypt(zlecenie.getZleceniodawca()));
            raportPdf.setNaglowki(List.of("Data","wykonanie","koszt","zleceniodawca"));
            raportPdf.dodajRzad(List.of(podsumowanie.getDataa(),
                    podsumowanie.getWykonane(),
                    podsumowanie.getKwota()+"",
                    zlecenie.getZleceniodawca()));
        }
    return raportPdf;
    }
}