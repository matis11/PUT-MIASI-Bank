package com.bartoszwalter.students.taxes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class TaxCalculator {

    public double podstawa = 0;
    public char umowa = ' ';
    // składki na ubezpieczenia społeczne
    public double s_emerytalna = 0; // 9,76% podstawyy
    public double s_rentowa = 0; // 1,5% podstawy
    public double u_chorobowe = 0; // 2,45% podstawy
    // składki na ubezpieczenia zdrowotne
    public double kosztyUzyskania = 111.25;
    public double s_zdrow1 = 0; // od podstawy wymiaru 9%
    public double s_zdrow2 = 0; // od podstawy wymiaru 7,75 %
    public double zaliczkaNaPod = 0; // zaliczka na podatek dochodowy 18%
    public double kwotaZmiejsz = 46.33; // kwota zmienjszająca podatek 46,33 PLN
    public double zaliczkaUS = 0;
    public double zaliczkaUS0 = 0;
    public double oPodstawa = 0;
    double podstawaOpodat = 0;
    double podstawaOpodat0 = 0;
    double podatekPotracony = 0;
    double wynagrodzenie = 0;

    public void run() {
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

        DecimalFormat df00 = new DecimalFormat("#.00");
        DecimalFormat df = new DecimalFormat("#");

        if (umowa == 'P') {
            obliczUOP(df00, df);
        } else if (umowa == 'Z') {
            obliczUZ(df00, df);
        } else {
            System.out.println("Nieznany typ umowy!");
        }
    }

    private void wypiszSkladki(DecimalFormat df00, DecimalFormat df) {
        System.out.println("Podstawa wymiaru składek " + podstawa);
        oPodstawa = obliczonaPodstawa(podstawa);
        System.out.println("Składka na ubezpieczenie emerytalne "
                + df00.format(s_emerytalna));
        System.out.println("Składka na ubezpieczenie rentowe    "
                + df00.format(s_rentowa));
        System.out.println("Składka na ubezpieczenie chorobowe  "
                + df00.format(u_chorobowe));
        System.out
                .println("Podstawa wymiaru składki na ubezpieczenie zdrowotne: "
                        + oPodstawa);
        obliczUbezpieczenia(oPodstawa);
        System.out.println("Składka na ubezpieczenie zdrowotne: 9% = "
                + df00.format(s_zdrow1) + " 7,75% = " + df00.format(s_zdrow2));
    }

    private void obliczUZ(DecimalFormat df00, DecimalFormat df) {

        wypiszSkladki(df00, df);
        kwotaZmiejsz = 0;
        kosztyUzyskania = (oPodstawa * 20) / 100;
        podstawaOpodat = oPodstawa - kosztyUzyskania;
        podstawaOpodat0 = Double.parseDouble(df.format(podstawaOpodat));
        obliczPodatek(podstawaOpodat0);
        podatekPotracony = zaliczkaNaPod;
        obliczZaliczke();
        zaliczkaUS0 = Double.parseDouble(df.format(zaliczkaUS));
        wynagrodzenie = podstawa
                - ((s_emerytalna + s_rentowa + u_chorobowe) + s_zdrow1 + zaliczkaUS0);

        wypiszUZ(df00, df);
    }

    private void wypiszUoP(DecimalFormat df00, DecimalFormat df) {
        System.out.println("UMOWA O PRACĘ");
        System.out.println("Koszty uzyskania przychodu w stałej wysokości "
                + kosztyUzyskania);
        System.out.println("Podstawa opodatkowania " + podstawaOpodat
                + " zaokrąglona " + df.format(podstawaOpodat0));
        System.out.println("Zaliczka na podatek dochodowy 18 % = "
                + zaliczkaNaPod);
        System.out.println("Kwota wolna od podatku = " + kwotaZmiejsz);
        System.out.println("Podatek potrącony = "
                + df00.format(podatekPotracony));
        System.out.println("Zaliczka do urzędu skarbowego = "
                + df00.format(zaliczkaUS) + " po zaokrągleniu = "
                + df.format(zaliczkaUS0));
        System.out.println();
        System.out
                .println("Pracownik otrzyma wynagrodzenie netto w wysokości = "
                        + df00.format(wynagrodzenie));
    }

    private void wypiszUZ(DecimalFormat df00, DecimalFormat df) {
        System.out.println("UMOWA-ZLECENIE");
        System.out.println("Koszty uzyskania przychodu (stałe) "
                + kosztyUzyskania);
        System.out.println("Podstawa opodatkowania " + podstawaOpodat
                + " zaokrąglona " + df.format(podstawaOpodat0));
        System.out.println("Zaliczka na podatek dochodowy 18 % = "
                + zaliczkaNaPod);
        System.out.println("Podatek potrącony = "
                + df00.format(podatekPotracony));
        System.out.println("Zaliczka do urzędu skarbowego = "
                + df00.format(zaliczkaUS) + " po zaokrągleniu = "
                + df.format(zaliczkaUS0));
        System.out.println();
        System.out
                .println("Pracownik otrzyma wynagrodzenie netto w wysokości = "
                        + df00.format(wynagrodzenie));
    }

    private void obliczUOP(DecimalFormat df00, DecimalFormat df) {
        wypiszSkladki(df00, df);
        podstawaOpodat = oPodstawa - kosztyUzyskania;
        podstawaOpodat0 = Double
                .parseDouble(df.format(podstawaOpodat));
        obliczPodatek(podstawaOpodat0);
        podatekPotracony = zaliczkaNaPod - kwotaZmiejsz;
        obliczZaliczke();
        zaliczkaUS0 = Double.parseDouble(df.format(zaliczkaUS));
        wynagrodzenie = podstawa
                - ((s_emerytalna + s_rentowa + u_chorobowe) + s_zdrow1 + zaliczkaUS0);

        wypiszUoP(df00, df);
    }


    public void obliczZaliczke() {
        zaliczkaUS = zaliczkaNaPod - s_zdrow2 - kwotaZmiejsz;
    }

    public void obliczPodatek(double podstawa) {
        zaliczkaNaPod = (podstawa * 18) / 100;
    }

    public double obliczonaPodstawa(double podstawa) {
        s_emerytalna = (podstawa * 9.76) / 100;
        s_rentowa = (podstawa * 1.5) / 100;
        u_chorobowe = (podstawa * 2.45) / 100;
        return (podstawa - s_emerytalna - s_rentowa - u_chorobowe);
    }

    public void obliczUbezpieczenia(double podstawa) {
        s_zdrow1 = (podstawa * 9) / 100;
        s_zdrow2 = (podstawa * 7.75) / 100;
    }
}
