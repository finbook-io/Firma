package io.finbook.model;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class SignData {

    private PrivateKey privateKey;
    private X509Certificate certificate;
    private final String textToSign;
    private byte[] sign;

    public SignData(String textToSign) {
        this.textToSign = textToSign;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    public String getTextToSign() {
        return textToSign;
    }

    public byte[] getSign() {
        return sign;
    }

    public void setSign(byte[] sign) {
        this.sign = sign;
    }
}
