package io.finbook;

import io.finbook.implementations.FirmaSwing;
import io.finbook.view.Firma;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            initFirma(new FirmaSwing("pepito", true));
        } else {
            initFirma(new FirmaSwing(args[0], true));
        }
    }

    private static void initFirma(Firma firma) {
        firma.init();
    }
}