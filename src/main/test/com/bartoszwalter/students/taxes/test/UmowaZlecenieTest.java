package com.bartoszwalter.students.taxes.test;

import com.bartoszwalter.students.taxes.UmowaZlecenie;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("ALL")
public class UmowaZlecenieTest {

    private double podstawa = 1000;
    private UmowaZlecenie umowaZlecenie;

    @Before
    public void setUp() throws Exception {
        this.umowaZlecenie = new UmowaZlecenie(podstawa);
    }

    @Test
    public void obliczKoszty() {
        umowaZlecenie.obliczKoszty();

        assertEquals(862.9, umowaZlecenie.oPodstawa, 1);
        assertEquals(1000, umowaZlecenie.podstawa, 1);
        assertEquals(77.661, umowaZlecenie.SkladkaZdrowotna1, 1);
        assertEquals(0, umowaZlecenie.kwotaZmiejszajacaPodatek, 1);
        assertEquals(97.6, umowaZlecenie.SkladkaEmerytalna, 1);
        assertEquals(24.5, umowaZlecenie.UbezpChorobowe, 1);
        assertEquals(172.58, umowaZlecenie.kosztyUzyskania, 1);
    }
}