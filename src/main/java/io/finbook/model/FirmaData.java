package io.finbook.model;

public class FirmaData {

    private String privateKeyPath;
    private String certificatePath;

    public FirmaData() {
    }

    public FirmaData(String privateKeyPath, String certificatePath) {
        this.privateKeyPath = privateKeyPath;
        this.certificatePath = certificatePath;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }
}
