package com.bartoszwalter.students.taxes;

public class Factory {
    public Umowa stworzUmowe(String type, double podstawa) {
        Umowa umowa = null;
        if (type.equals("Z")) {
            umowa = new UmowaZlecenie(podstawa);
        } else if (type.equals("P")) {
            umowa = new UmowaOPrace(podstawa);
        }
        return umowa;
    }
}
