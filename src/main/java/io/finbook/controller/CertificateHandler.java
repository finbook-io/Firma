package io.finbook.controller;

import io.finbook.model.FirmaData;
import io.finbook.model.SignData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertificateHandler extends FirmaHandler {

    private FirmaData firmaData;
    private SignData signData;
    private final static String CERTIFICATE_PATH = "certificate_path.txt";
    private final static File certificatePathFile;

    static {
        certificatePathFile = new File(CERTIFICATE_PATH);
        create(certificatePathFile);
    }

    public CertificateHandler(FirmaData firmaData, SignData signData) {
        Security.setProperty("crypto.policy", "unlimited");
        try {
            javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        } catch (NoSuchAlgorithmException ignored) {}
        Security.addProvider(new BouncyCastleProvider());

        this.firmaData = firmaData;
        this.signData = signData;
    }

    public void setCertificatePath() {
        firmaData.setCertificatePath(getCertificatePath());
    }

    private String getCertificatePath() {
        String certificatePath = null;

        try {
            certificatePath = fromBufferedReader().readLine();
        } catch (IOException ignored) {}

        return certificatePath;
    }

    private BufferedReader fromBufferedReader() {
        try {
            return new BufferedReader(new FileReader(certificatePathFile));
        } catch (IOException e) {
            return null;
        }
    }

    public void getCertificate() throws InvalidCertificate {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");

            try {
                signData.setCertificate((X509Certificate) certFactory.generateCertificate(new FileInputStream(firmaData.getCertificatePath())));
            } catch (FileNotFoundException | CertificateException e) {
                throw new InvalidCertificate();
            }
        } catch (NoSuchProviderException | CertificateException ignored) {}
    }
}
