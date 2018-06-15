package com.bartoszwalter.students.taxes;

import org.junit.Test;

import static org.junit.Assert.*;

public class FactoryTest {

    private float podstawa = 1000;
    private String typZlecenie = "Z";
    private String typOPrace = "P";
    private UmowaZlecenie umowaZlecenie = new UmowaZlecenie(podstawa);
    private UmowaOPrace umowaOPrace = new UmowaOPrace(podstawa);

    @Test
    public void stworzUmoweZlecenie() {
        final Factory factory = new Factory();
        final Umowa umowa = factory.stworzUmowe("Z", podstawa);

        assertEquals(umowaZlecenie, umowa);
    }

    @Test
    public void stworzUmoweOPrace() {
        final Factory factory = new Factory();
        final Umowa umowa = factory.stworzUmowe("P", podstawa);

        assertEquals(umowaOPrace, umowa);
    }
}