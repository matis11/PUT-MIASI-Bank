package com.bartoszwalter.students.taxes;

import com.sun.istack.internal.Nullable;

public class Factory {

    @Nullable
    public Umowa stworzUmowe(String type, double podstawa) {
        Umowa umowa = null;

        if (type.equals(UmowaZlecenie.symbol)) {
            umowa = new UmowaZlecenie(podstawa);
        } else if (type.equals(UmowaOPrace.symbol)) {
            umowa = new UmowaOPrace(podstawa);
        } else {
            System.out.println("Nie rozpoznano typu umowy");
        }

        return umowa;
    }
}
