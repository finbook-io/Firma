package io.finbook.model;

public class FirmaData {

    private final String privateKeyPath;
    private final String certificatePath;

    public FirmaData(String privateKeyPath, String certificatePath) {
        this.privateKeyPath = privateKeyPath;
        this.certificatePath = certificatePath;
    }
}
