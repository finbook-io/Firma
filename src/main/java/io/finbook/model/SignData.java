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
}
