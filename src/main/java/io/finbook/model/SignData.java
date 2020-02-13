package io.finbook.model;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class SignData {

    private final PrivateKey privateKey;
    private final X509Certificate certificate;
    private final String textToSign;
    private byte[] sign;

    public SignData(PrivateKey privateKey, X509Certificate certificate, String textToSign) {
        this.privateKey = privateKey;
        this.certificate = certificate;
        this.textToSign = textToSign;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public X509Certificate getCertificate() {
        return certificate;
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
