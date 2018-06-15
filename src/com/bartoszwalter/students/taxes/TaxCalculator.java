package com.bartoszwalter.students.taxes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class TaxCalculator {

    // składki na ubezpieczenia społeczne
    private double skladkaEmerytalna = 0; // 9,76% podstawyy
    private double skladkaRentowa = 0; // 1,5% podstawy
    private double ubezpChorobowe = 0; // 2,45% podstawy

    // składki na ubezpieczenia zdrowotne
    private double kosztyUzyskania = 111.25;
    private double skladkaZdrowotna1 = 0; // od podstawy wymiaru 9%
    private double skladkaZdrowotna2 = 0; // od podstawy wymiaru 7,75 %
    private double zaliczkaNaPodatekDochodowy = 0; // zaliczka na podatek dochodowy 18%
    private double kwotaZmiejszajacaPodatek = 46.33; // kwota zmienjszająca podatek 46,33 PLN
    private double zaliczkaUS = 0;
    private double zaliczkaUSZaokr = 0;
    private double obliczonaPodstawa = 0;
    private double podstawaOpodat = 0;
    private double podstawaOpodatZaokr = 0;
    private double podatekPotracony = 0;
    private double wynagrodzenie = 0;

    private final DecimalFormat formatZDwomaZerami = new DecimalFormat("#.00");
    private final DecimalFormat formatBezZer = new DecimalFormat("#");

    public void run() {
        final Factory factory = new Factory();
        final char typ;

        Double podstawa = 0.0;

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
        System.out.println("Składka na ubezpieczenie emerytalne "
                + formatZDwomaZerami.format(skladkaEmerytalna));
        System.out.println("Składka na ubezpieczenie rentowe    "
                + formatZDwomaZerami.format(skladkaRentowa));
        System.out.println("Składka na ubezpieczenie chorobowe  "
                + formatZDwomaZerami.format(ubezpChorobowe));
        System.out
                .println("Podstawa wymiaru składki na ubezpieczenie zdrowotne: "
                        + obliczonaPodstawa);
        obliczUbezpieczenia(obliczonaPodstawa);
      /*  System.out.println("Składka na ubezpieczenie zdrowotne: 9% = "
                + formatZDwomaZerami.format(skladkaZdrowotna1) + " 7,75% = " + formatZDwomaZerami.format(skladkaZdrowotna2));*/
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
        System.out.println("Koszty uzyskania przychodu w stałej wysokości "
                + kosztyUzyskania);
        System.out.println("Podstawa opodatkowania " + podstawaOpodat
                + " zaokrąglona " + formatBezZer.format(podstawaOpodatZaokr));
        System.out.println("Zaliczka na podatek dochodowy 18 % = "
                + zaliczkaNaPodatekDochodowy);
        System.out.println("Kwota wolna od podatku = " + kwotaZmiejszajacaPodatek);
        System.out.println("Podatek potrącony = "
                + formatZDwomaZerami.format(podatekPotracony));
        System.out.println("Zaliczka do urzędu skarbowego = "
                + formatZDwomaZerami.format(zaliczkaUS) + " po zaokrągleniu = "
                + formatBezZer.format(zaliczkaUSZaokr));
        System.out.println();
        System.out
                .println("Pracownik otrzyma wynagrodzenie netto w wysokości = "
                        + formatZDwomaZerami.format(wynagrodzenie));
    }

    private void wypiszUZ() {
        System.out.println("UMOWA-ZLECENIE");
        System.out.println("Koszty uzyskania przychodu (stałe) "
                + kosztyUzyskania);
        System.out.println("Podstawa opodatkowania " + podstawaOpodat
                + " zaokrąglona " + formatBezZer.format(podstawaOpodatZaokr));
        System.out.println("Zaliczka na podatek dochodowy 18 % = "
                + zaliczkaNaPodatekDochodowy);
        System.out.println("Podatek potrącony = "
                + formatZDwomaZerami.format(podatekPotracony));
        System.out.println("Zaliczka do urzędu skarbowego = "
                + formatZDwomaZerami.format(zaliczkaUS) + " po zaokrągleniu = "
                + formatBezZer.format(zaliczkaUSZaokr));
        System.out.println();
        System.out
                .println("Pracownik otrzyma wynagrodzenie netto w wysokości = "
                        + formatZDwomaZerami.format(wynagrodzenie));
    }

    public Double obliczZaliczke() {
        return zaliczkaNaPodatekDochodowy - skladkaZdrowotna2 - kwotaZmiejszajacaPodatek;
    }

    public Double obliczPodatek(double podstawa) {
        return (podstawa * 18) / 100;
    }

    public double obliczPodstawe(double podstawa) {
        skladkaEmerytalna = (podstawa * 9.76) / 100;
        skladkaRentowa = (podstawa * 1.5) / 100;
        ubezpChorobowe = (podstawa * 2.45) / 100;
        return (podstawa - skladkaEmerytalna - skladkaRentowa - ubezpChorobowe);
    }

    public void obliczUbezpieczenia(double podstawa) {
        skladkaZdrowotna1 = (podstawa * 9) / 100;
        skladkaZdrowotna2 = (podstawa * 7.75) / 100;
    }
}
