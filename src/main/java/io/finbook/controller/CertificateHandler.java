package io.finbook.controller;

import io.finbook.model.FirmaData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertificateHandler {

    private FirmaData firmaData;
    private static File certificateData;

    static {
        certificateData = new File("certificate_path.txt");
        createFile();
    }

    private static void createFile() {
        try {
            certificateData.createNewFile();
        } catch (IOException ignored) {}
    }

    public CertificateHandler(FirmaData firmaData) {
        Security.setProperty("crypto.policy", "unlimited");
        try {
            javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        } catch (NoSuchAlgorithmException ignored) {}
        Security.addProvider(new BouncyCastleProvider());

        this.firmaData = firmaData;
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
            return new BufferedReader(new FileReader(certificateData));
        } catch (IOException e) {
            return null;
        }
    }

    public X509Certificate getCertificate() throws InvalidCertificate {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");

            try {
                return (X509Certificate) certFactory.generateCertificate(new FileInputStream(firmaData.getCertificatePath()));
            } catch (FileNotFoundException | CertificateException e) {
                throw new InvalidCertificate();
            }
        } catch (NoSuchProviderException | CertificateException e) {
            return null;
        }
    }

    public static class InvalidCertificate extends Exception {}
}
