package com.bartoszwalter.students.taxes.test;

import com.bartoszwalter.students.taxes.Factory;
import com.bartoszwalter.students.taxes.Umowa;
import com.bartoszwalter.students.taxes.UmowaOPrace;
import com.bartoszwalter.students.taxes.UmowaZlecenie;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FactoryTest {

    private float podstawa = 1000;
    private String typZlecenie = "Z";
    private String typOPrace = "P";
    private UmowaZlecenie umowaZlecenie = new UmowaZlecenie(podstawa);
    private UmowaOPrace umowaOPrace = new UmowaOPrace(podstawa);

    @Test
    public void stworzUmoweZlecenie() {
        final Factory factory = new Factory();
        final Umowa umowa = factory.stworzUmowe(typZlecenie, podstawa);

        assertEquals(umowaZlecenie, umowa);
    }

    @Test
    public void stworzUmoweOPrace() {
        final Factory factory = new Factory();
        final Umowa umowa = factory.stworzUmowe(typOPrace, podstawa);

        assertEquals(umowaOPrace, umowa);
    }
}