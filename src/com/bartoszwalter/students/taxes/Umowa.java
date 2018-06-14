package com.bartoszwalter.students.taxes;

import java.text.DecimalFormat;
import java.util.HashMap;

public abstract class Umowa {
    public abstract void obliczKoszty();
    public abstract void wypiszKoszty();
    public abstract double obliczZaliczke();

    public double SkladkaEmerytalna = 0; // 9,76% podstawyy
    public double SkladkaRentowa = 0; // 1,5% podstawy
    public double UbezpChorobowe = 0; // 2,45% podstawy
    public double SkladkaZdrowotna1 = 0; // od podstawy wymiaru 9%
    public double SkladkaZdrowotna2 = 0; // od podstawy wymiaru 7,75 %
    public double oPodstawa = 0;
    public double kwotaZmiejszajacaPodatek = 46.33; // kwota zmienjszająca podatek 46,33 PLN
    public double podstawa;
    public double obliczonaPodstawa(double podstawa) {
        SkladkaEmerytalna = (podstawa * 9.76) / 100;
        SkladkaRentowa = (podstawa * 1.5) / 100;
        UbezpChorobowe = (podstawa * 2.45) / 100;
        return (podstawa - SkladkaEmerytalna - SkladkaRentowa - UbezpChorobowe);
    }

    public Umowa(double podstawa) {
        this.podstawa = podstawa;
    }

    public void obliczUbezpieczenia(double podstawa) {
        SkladkaZdrowotna1 = (podstawa * 9) / 100;
        SkladkaZdrowotna2 = (podstawa * 7.75) / 100;
    }

    public void wypiszSkladki() {
        DecimalFormat df00 = new DecimalFormat("#.00");
        DecimalFormat df = new DecimalFormat("#");

        System.out.println("Podstawa wymiaru składek " + podstawa);
//        oPodstawa = obliczonaPodstawa(podstawa); //moved to UmowaOPrace -> obliczKoszty
        System.out.println("Składka na ubezpieczenie emerytalne "
                + df00.format(SkladkaEmerytalna));
        System.out.println("Składka na ubezpieczenie rentowe    "
                + df00.format(SkladkaRentowa));
        System.out.println("Składka na ubezpieczenie chorobowe  "
                + df00.format(UbezpChorobowe));
        System.out
                .println("Podstawa wymiaru składki na ubezpieczenie zdrowotne: "
                        + oPodstawa);
        obliczUbezpieczenia(oPodstawa);
        System.out.println("Składka na ubezpieczenie zdrowotne: 9% = "
                + df00.format(SkladkaZdrowotna1) + " 7,75% = " + df00.format(SkladkaZdrowotna2));
    }

    public Double obliczPodatek(double podstawa) {
        return (podstawa * 18) / 100;
    }


}
