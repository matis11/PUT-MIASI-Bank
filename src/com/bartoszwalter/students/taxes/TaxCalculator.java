package com.bartoszwalter.students.taxes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class TaxCalculator {

    private static final int PROCENTY_PODATEK_DOCHODOWY = 18;
    private static final double PROCENTY_SKLADKA_EMERYTALNA = 9.76;
    private static final double PROCENTY_SKLADKA_RENTOWA = 1.5;
    private static final double PROCENTY_SKLADKA_UBEZP_CHOROBOWE = 2.45;
    private static final int PROCENTY_SKLADKA_ZDROWOTNA_1 = 9;
    private static final double PROCENTY_SKLADKA_ZDROWOTNA_2 = 7.75;

    // składki na ubezpieczenia społeczne
    private double skladkaEmerytalna;
    private double skladkaRentowa;
    private double ubezpChorobowe;

    // składki na ubezpieczenia zdrowotne
    private double kosztyUzyskania;
    private double skladkaZdrowotna1;
    private double skladkaZdrowotna2;
    private double zaliczkaNaPodatekDochodowy;
    private double kwotaZmiejszajacaPodatek;
    private double zaliczkaUS;
    private double zaliczkaUSZaokr;
    private double obliczonaPodstawa;
    private double podstawaOpodat;
    private double podstawaOpodatZaokr;
    private double podatekPotracony;
    private double wynagrodzenie;

    private final DecimalFormat formatZDwomaZerami = new DecimalFormat("#.00");
    private final DecimalFormat formatBezZer = new DecimalFormat("#");

    public void run() {
        final Factory factory = new Factory();
        final char typ;

        Double podstawa;

        try {
            final InputStreamReader isr = new InputStreamReader(System.in);
            final BufferedReader br = new BufferedReader(isr);

            System.out.print("Podaj kwotę dochodu: ");
            podstawa = Double.parseDouble(br.readLine());

            System.out.print("Typ umowy: (P)raca, (Z)lecenie: ");
            typ = br.readLine().charAt(0);

        } catch (Exception ex) {
            System.out.println("Błędne dane");
            System.err.println(ex);
            return;
        }

        final String typString = "" + typ;
        final Umowa umowa = factory.stworzUmowe(typString, podstawa);
        umowa.obliczKoszty();
        umowa.wypiszKoszty();
    }

    private void wypiszSkladki(Double podstawa) {
        System.out.println("Podstawa wymiaru składek " + podstawa);
        obliczonaPodstawa = obliczPodstawe(podstawa);
        System.out.println("Składka na ubezpieczenie emerytalne " + formatZDwomaZerami.format(skladkaEmerytalna));
        System.out.println("Składka na ubezpieczenie rentowe    " + formatZDwomaZerami.format(skladkaRentowa));
        System.out.println("Składka na ubezpieczenie chorobowe  " + formatZDwomaZerami.format(ubezpChorobowe));
        System.out.println("Podstawa wymiaru składki na ubezpieczenie zdrowotne: " + obliczonaPodstawa);

        obliczUbezpieczenia(obliczonaPodstawa);
    }

    private void obliczUZ(Double podstawa) {
        wypiszSkladki(podstawa);

        kwotaZmiejszajacaPodatek = 0;
        kosztyUzyskania = (obliczonaPodstawa * 20) / 100;
        podstawaOpodat = obliczonaPodstawa - kosztyUzyskania;
        podstawaOpodatZaokr = Double.parseDouble(formatBezZer.format(podstawaOpodat));
        zaliczkaNaPodatekDochodowy = obliczPodatek(podstawaOpodatZaokr);
        podatekPotracony = zaliczkaNaPodatekDochodowy;
        zaliczkaUS = obliczZaliczke();
        zaliczkaUSZaokr = Double.parseDouble(formatBezZer.format(zaliczkaUS));
        wynagrodzenie = podstawa - ((skladkaEmerytalna + skladkaRentowa + ubezpChorobowe) + skladkaZdrowotna1 + zaliczkaUSZaokr);

        wypiszUZ();
    }

    public void obliczUOP(Double podstawa) {
        wypiszSkladki(podstawa);
        podstawaOpodat = obliczonaPodstawa - kosztyUzyskania;
        podstawaOpodatZaokr = Double.parseDouble(formatBezZer.format(podstawaOpodat));
        zaliczkaNaPodatekDochodowy = obliczPodatek(podstawaOpodatZaokr);
        podatekPotracony = zaliczkaNaPodatekDochodowy - kwotaZmiejszajacaPodatek;
        zaliczkaUS = obliczZaliczke();
        zaliczkaUSZaokr = Double.parseDouble(formatBezZer.format(zaliczkaUS));
        wynagrodzenie = podstawa - ((skladkaEmerytalna + skladkaRentowa + ubezpChorobowe) + skladkaZdrowotna1 + zaliczkaUSZaokr);

        wypiszUoP(podstawa);
    }

    private void wypiszUoP(Double podstawa) {
        System.out.println("UMOWA O PRACĘ");
        System.out.println("Koszty uzyskania przychodu w stałej wysokości " + kosztyUzyskania);
        System.out.println("Podstawa opodatkowania " + podstawaOpodat + " zaokrąglona " + formatBezZer.format(podstawaOpodatZaokr));
        System.out.println("Zaliczka na podatek dochodowy 18 % = " + zaliczkaNaPodatekDochodowy);
        System.out.println("Kwota wolna od podatku = " + kwotaZmiejszajacaPodatek);
        System.out.println("Podatek potrącony = " + formatZDwomaZerami.format(podatekPotracony));
        System.out.println("Zaliczka do urzędu skarbowego = "
                + formatZDwomaZerami.format(zaliczkaUS) + " po zaokrągleniu = "
                + formatBezZer.format(zaliczkaUSZaokr));
        System.out.println();
        System.out.println("Pracownik otrzyma wynagrodzenie netto w wysokości = " + formatZDwomaZerami.format(wynagrodzenie));
    }

    private void wypiszUZ() {
        System.out.println("UMOWA-ZLECENIE");
        System.out.println("Koszty uzyskania przychodu (stałe) " + kosztyUzyskania);
        System.out.println("Podstawa opodatkowania " + podstawaOpodat + " zaokrąglona " + formatBezZer.format(podstawaOpodatZaokr));
        System.out.println("Zaliczka na podatek dochodowy 18 % = " + zaliczkaNaPodatekDochodowy);
        System.out.println("Podatek potrącony = " + formatZDwomaZerami.format(podatekPotracony));
        System.out.println("Zaliczka do urzędu skarbowego = "
                + formatZDwomaZerami.format(zaliczkaUS) + " po zaokrągleniu = "
                + formatBezZer.format(zaliczkaUSZaokr));
        System.out.println();
        System.out.println("Pracownik otrzyma wynagrodzenie netto w wysokości = " + formatZDwomaZerami.format(wynagrodzenie));
    }

    private Double obliczZaliczke() {
        return zaliczkaNaPodatekDochodowy - skladkaZdrowotna2 - kwotaZmiejszajacaPodatek;
    }

    private Double obliczPodatek(double podstawa) {
        return procentZCalosci(PROCENTY_PODATEK_DOCHODOWY, podstawa);
    }

    private double obliczPodstawe(double podstawa) {
        skladkaEmerytalna = procentZCalosci(PROCENTY_SKLADKA_EMERYTALNA, podstawa);
        skladkaRentowa = procentZCalosci(PROCENTY_SKLADKA_RENTOWA, podstawa);
        ubezpChorobowe = procentZCalosci(PROCENTY_SKLADKA_UBEZP_CHOROBOWE, podstawa);

        return (podstawa - skladkaEmerytalna - skladkaRentowa - ubezpChorobowe);
    }

    private void obliczUbezpieczenia(double podstawa) {
        skladkaZdrowotna1 = procentZCalosci(PROCENTY_SKLADKA_ZDROWOTNA_1, podstawa);
        skladkaZdrowotna2 = procentZCalosci(PROCENTY_SKLADKA_ZDROWOTNA_2, podstawa);
    }

    private static double procentZCalosci(double procenty, double calosc) {
        return (calosc * procenty) / 100;
    }
}
