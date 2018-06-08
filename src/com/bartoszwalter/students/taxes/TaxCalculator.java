package com.bartoszwalter.students.taxes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;

public class TaxCalculator {

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
    public double zaliczkaUSZaokr = 0;
    public double oPodstawa = 0;
    double podstawaOpodat = 0;
    double podstawaOpodatZaokr = 0;
    double podatekPotracony = 0;
    double wynagrodzenie = 0;
    DecimalFormat df00 = new DecimalFormat("#.00");
    DecimalFormat df = new DecimalFormat("#");

    public void run() {
        Double podstawa = 0.0;
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

    private void obliczUZ(Double podstawa) {
        wypiszSkladki(podstawa);
        kwotaZmiejsz = 0;
        kosztyUzyskania = (oPodstawa * 20) / 100;
        podstawaOpodat = oPodstawa - kosztyUzyskania;
        podstawaOpodatZaokr = Double.parseDouble(df.format(podstawaOpodat));
        zaliczkaNaPod = obliczPodatek(podstawaOpodatZaokr);
        podatekPotracony = zaliczkaNaPod;
        zaliczkaUS = obliczZaliczke();
        zaliczkaUSZaokr = Double.parseDouble(df.format(zaliczkaUS));
        wynagrodzenie = podstawa - ((s_emerytalna + s_rentowa + u_chorobowe) + s_zdrow1 + zaliczkaUSZaokr);

        wypiszUZ();
    }

    private void obliczUOP(Double podstawa) {
        HashMap<String, Double> skladowePlacy = new HashMap<>();
        wypiszSkladki(podstawa);
        podstawaOpodat = oPodstawa - kosztyUzyskania;
        skladowePlacy.put("PodstawaOpodatkowania", podstawaOpodat);
        podstawaOpodatZaokr = Double.parseDouble(df.format(podstawaOpodat));
        skladowePlacy.put("PodstawaOpodatkowaniaZaokraglona", podstawaOpodatZaokr);
        zaliczkaNaPod = obliczPodatek(podstawaOpodatZaokr);
        skladowePlacy.put("ZaliczkaNaPodatek", zaliczkaNaPod);
        podatekPotracony = zaliczkaNaPod - kwotaZmiejsz;
        skladowePlacy.put("PodatekPotracony", podatekPotracony);
        zaliczkaUS = obliczZaliczke();
        skladowePlacy.put("ZaliczkaUS", zaliczkaUS);
        zaliczkaUSZaokr = Double.parseDouble(df.format(zaliczkaUS));
        skladowePlacy.put("ZaliczkaUSZaokraglona", zaliczkaUSZaokr);
        wynagrodzenie = podstawa - ((s_emerytalna + s_rentowa + u_chorobowe) + s_zdrow1 + zaliczkaUSZaokr);

        wypiszUoP(podstawa);
    }

    private void wypiszUoP(Double podstawa) {
        System.out.println("UMOWA O PRACĘ");
        System.out.println("Koszty uzyskania przychodu w stałej wysokości "
                + kosztyUzyskania);
        System.out.println("Podstawa opodatkowania " + podstawaOpodat
                + " zaokrąglona " + df.format(podstawaOpodatZaokr));
        System.out.println("Zaliczka na podatek dochodowy 18 % = "
                + zaliczkaNaPod);
        System.out.println("Kwota wolna od podatku = " + kwotaZmiejsz);
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
                + zaliczkaNaPod);
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
        return zaliczkaNaPod - s_zdrow2 - kwotaZmiejsz;
    }

    public Double obliczPodatek(double podstawa) {
        return (podstawa * 18) / 100;
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
