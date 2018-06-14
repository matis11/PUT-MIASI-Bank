package com.bartoszwalter.students.taxes;

public class Factory {
    public Umowa stworzUmowe(String type, double podstawa) {
        Umowa umowa = null;
        if (type.equals("zlecenie")) {
            umowa = new UmowaZlecenie(podstawa);
        } else if (type.equals("praca")) {
            umowa = new UmowaOPrace(podstawa);
        }
        return umowa;
    }
}
