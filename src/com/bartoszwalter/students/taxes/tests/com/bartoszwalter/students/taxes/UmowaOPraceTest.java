package com.bartoszwalter.students.taxes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UmowaOPraceTest {

    private double podstawa = 1000;
    private UmowaOPrace umowaOPrace;

    @Before
    public void setUp() throws Exception {
        this.umowaOPrace = new UmowaOPrace(podstawa);
    }

    @Test
    public void obliczKoszty() {
        umowaOPrace.obliczKoszty();

        assertEquals(0, umowaOPrace.oPodstawa, 1);
        assertEquals(1000, umowaOPrace.podstawa, 1);
        assertEquals(77.661, umowaOPrace.SkladkaZdrowotna1, 1);
        assertEquals(66.87475, umowaOPrace.SkladkaZdrowotna2, 1);
        assertEquals(46.33, umowaOPrace.kwotaZmiejszajacaPodatek, 1);
        assertEquals(97.6, umowaOPrace.SkladkaEmerytalna, 1);
        assertEquals(24.5, umowaOPrace.UbezpChorobowe, 1);
        assertEquals(111.25, umowaOPrace.kosztyUzyskania, 1);
        assertEquals(763.239, umowaOPrace.wynagrodzenie, 1);
    }
}