package io.finbook;

import io.finbook.implementations.FirmaSwing;
import io.finbook.view.Firma;

public class Main {
    public static void main(String[] args) {
        initFirma(new FirmaSwing("Texto de ejemplo"));
    }

    private static void initFirma(Firma firma) {
        firma.init();
    }
}
