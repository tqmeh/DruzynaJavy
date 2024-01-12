package com.example.Javaproject1;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class LimitowanyTextTest {

    //Sprawdzajacy czy wpisano tylko liczby
    @Test
    void wpisanoTylkoLiczby() {
        LimitowanyText limitowanyText = new LimitowanyText(5, true);
    }



}