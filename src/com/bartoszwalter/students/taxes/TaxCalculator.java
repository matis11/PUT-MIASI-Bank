package com.bartoszwalter.students.taxes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;

public class TaxCalculator {

    // składki na ubezpieczenia społeczne
    private double SkladkaEmerytalna = 0; // 9,76% podstawyy
    private double SkladkaRentowa = 0; // 1,5% podstawy
    private double UbezpChorobowe = 0; // 2,45% podstawy
    // składki na ubezpieczenia zdrowotne
    private double kosztyUzyskania = 111.25;
    private double SkladkaZdrowotna1 = 0; // od podstawy wymiaru 9%
    private double SkladkaZdrowotna2 = 0; // od podstawy wymiaru 7,75 %
    private double zaliczkaNaPodatekDochodowy = 0; // zaliczka na podatek dochodowy 18%
    private double kwotaZmiejszajacaPodatek = 46.33; // kwota zmienjszająca podatek 46,33 PLN
    private double zaliczkaUS = 0;
    private double zaliczkaUSZaokr = 0;
    private double oPodstawa = 0;
    private double podstawaOpodat = 0;
    private double podstawaOpodatZaokr = 0;
    private double podatekPotracony = 0;
    private double wynagrodzenie = 0;
    DecimalFormat df00 = new DecimalFormat("#.00");
    DecimalFormat df = new DecimalFormat("#");

    public void run() {
        Double podstawa = 0.0;
        char umowa;
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);

            System.out.print("Podaj kwotę dochodu: ");
            podstawa = Double.parseDouble(br.readLine());

            System.out.print("Typ umowy: (P)raca, (Z)lecenie: ");
            umowa = br.readLine().charAt(0);

        } catch (Exception ex) {
            System.out.println("Błędna kwota");
            System.err.println(ex);
            return;
        }

        if (umowa == 'P') {
            obliczUOP(podstawa);
        } else if (umowa == 'Z') {
            obliczUZ(podstawa);
        } else {
            System.out.println("Nieznany typ umowy!");
        }
    }

    private void wypiszSkladki(Double podstawa) {
        System.out.println("Podstawa wymiaru składek " + podstawa);
        oPodstawa = obliczonaPodstawa(podstawa);
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

    private void obliczUZ(Double podstawa) {
        wypiszSkladki(podstawa);
        kwotaZmiejszajacaPodatek = 0;
        kosztyUzyskania = (oPodstawa * 20) / 100;
        podstawaOpodat = oPodstawa - kosztyUzyskania;
        podstawaOpodatZaokr = Double.parseDouble(df.format(podstawaOpodat));
        zaliczkaNaPodatekDochodowy = obliczPodatek(podstawaOpodatZaokr);
        podatekPotracony = zaliczkaNaPodatekDochodowy;
        zaliczkaUS = obliczZaliczke();
        zaliczkaUSZaokr = Double.parseDouble(df.format(zaliczkaUS));
        wynagrodzenie = podstawa - ((SkladkaEmerytalna + SkladkaRentowa + UbezpChorobowe) + SkladkaZdrowotna1 + zaliczkaUSZaokr);

        wypiszUZ();
    }

    private void obliczUOP(Double podstawa) {
        wypiszSkladki(podstawa);
        podstawaOpodat = oPodstawa - kosztyUzyskania;
        podstawaOpodatZaokr = Double.parseDouble(df.format(podstawaOpodat));
        zaliczkaNaPodatekDochodowy = obliczPodatek(podstawaOpodatZaokr);
        podatekPotracony = zaliczkaNaPodatekDochodowy - kwotaZmiejszajacaPodatek;
        zaliczkaUS = obliczZaliczke();
        zaliczkaUSZaokr = Double.parseDouble(df.format(zaliczkaUS));
        wynagrodzenie = podstawa - ((SkladkaEmerytalna + SkladkaRentowa + UbezpChorobowe) + SkladkaZdrowotna1 + zaliczkaUSZaokr);

        wypiszUoP(podstawa);
    }

    private void wypiszUoP(Double podstawa) {
        System.out.println("UMOWA O PRACĘ");
        System.out.println("Koszty uzyskania przychodu w stałej wysokości "
                + kosztyUzyskania);
        System.out.println("Podstawa opodatkowania " + podstawaOpodat
                + " zaokrąglona " + df.format(podstawaOpodatZaokr));
        System.out.println("Zaliczka na podatek dochodowy 18 % = "
                + zaliczkaNaPodatekDochodowy);
        System.out.println("Kwota wolna od podatku = " + kwotaZmiejszajacaPodatek);
        System.out.println("Podatek potrącony = "
                + df00.format(podatekPotracony));
        System.out.println("Zaliczka do urzędu skarbowego = "
                + df00.format(zaliczkaUS) + " po zaokrągleniu = "
                + df.format(zaliczkaUSZaokr));
        System.out.println();
        System.out
                .println("Pracownik otrzyma wynagrodzenie netto w wysokości = "
                        + df00.format(wynagrodzenie));
    }

    private void wypiszUZ() {
        System.out.println("UMOWA-ZLECENIE");
        System.out.println("Koszty uzyskania przychodu (stałe) "
                + kosztyUzyskania);
        System.out.println("Podstawa opodatkowania " + podstawaOpodat
                + " zaokrąglona " + df.format(podstawaOpodatZaokr));
        System.out.println("Zaliczka na podatek dochodowy 18 % = "
                + zaliczkaNaPodatekDochodowy);
        System.out.println("Podatek potrącony = "
                + df00.format(podatekPotracony));
        System.out.println("Zaliczka do urzędu skarbowego = "
                + df00.format(zaliczkaUS) + " po zaokrągleniu = "
                + df.format(zaliczkaUSZaokr));
        System.out.println();
        System.out
                .println("Pracownik otrzyma wynagrodzenie netto w wysokości = "
                        + df00.format(wynagrodzenie));
    }

    public Double obliczZaliczke() {
        return zaliczkaNaPodatekDochodowy - SkladkaZdrowotna2 - kwotaZmiejszajacaPodatek;
    }

    public Double obliczPodatek(double podstawa) {
        return (podstawa * 18) / 100;
    }

    public double obliczonaPodstawa(double podstawa) {
        SkladkaEmerytalna = (podstawa * 9.76) / 100;
        SkladkaRentowa = (podstawa * 1.5) / 100;
        UbezpChorobowe = (podstawa * 2.45) / 100;
        return (podstawa - SkladkaEmerytalna - SkladkaRentowa - UbezpChorobowe);
    }

    public void obliczUbezpieczenia(double podstawa) {
        SkladkaZdrowotna1 = (podstawa * 9) / 100;
        SkladkaZdrowotna2 = (podstawa * 7.75) / 100;
    }
}
