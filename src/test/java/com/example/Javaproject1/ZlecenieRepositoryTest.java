package com.example.Javaproject1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@DataJpaTest
@SpringBootTest
@RunWith(SpringRunner.class)
class ZlecenieRepositoryTest {

    @Autowired
    ZlecenieRepository zlecenieRepository;

    @Test
    void dodanieDanychDoBazy() {
        Zlecenie zlecenie = new Zlecenie();

        System.out.println("Dodaje nowego");
        zlecenie.setNumer(10);
        zlecenie.setZleceniodawca("test");
        zlecenie.setPrzyjecie("10.01.2024");
        zlecenie.setUrzadzenie("laptop");
        zlecenie.setMarka("HP");
        zlecenie.setModel("14s");
        zlecenie.setSeryjny("12321421");
        zlecenie.setUsterka("Usterka");
        zlecenie.setWady("Brak wad");
        zlecenie.setUwagi("Brak");
        zlecenie.setZakonczenie("10-01-2024");
        zlecenie.setStatus("Zakonczone");


        Zlecenie zapisaneZlecenie = zlecenieRepository.save(zlecenie);

        Zlecenie otrzymaneZlecenie = zlecenieRepository.findByNumer(zapisaneZlecenie.getNumer()).orElse(null);

        assertNotNull(otrzymaneZlecenie);
        assertEquals("Brak wad", otrzymaneZlecenie.getWady());
        assertEquals("Zakonczone", otrzymaneZlecenie.getStatus());
    }
}