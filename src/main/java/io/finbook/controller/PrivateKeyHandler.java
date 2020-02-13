package io.finbook.controller;

import io.finbook.model.FirmaData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class PrivateKeyHandler {

    private FirmaData firmaData;
    private X509Certificate certificate;
    private final static String PRIVATE_KEY_PATH = "private_key_path.txt";
    private final static File privateKeyData;

    static {
        privateKeyData = new File(PRIVATE_KEY_PATH);
        createFile();
    }

    private static void createFile() {
        try {
            privateKeyData.createNewFile();
        } catch (IOException ignored) {}
    }

    public PrivateKeyHandler(FirmaData firmaData, X509Certificate certificate) {
        Security.setProperty("crypto.policy", "unlimited");
        try {
            javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        } catch (NoSuchAlgorithmException ignored) {}
        Security.addProvider(new BouncyCastleProvider());

        this.firmaData = firmaData;
        this.certificate = certificate;
    }

    public void setPrivateKeyPath() {
        firmaData.setPrivateKeyPath(getPrivateKeyPath());
    }

    private String getPrivateKeyPath() {
        String privateKeyPath = null;

        try {
            privateKeyPath = fromBufferedReader().readLine();
        } catch (IOException ignored) {}

        return privateKeyPath;
    }

    private BufferedReader fromBufferedReader() {
        try {
            return new BufferedReader(new FileReader(privateKeyData));
        } catch (IOException e) {
            return null;
        }
    }

    public PrivateKey getPrivateKey(String password) throws InvalidPassword, InvalidCertificate {
        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            try {
                keystore.load(new FileInputStream(firmaData.getPrivateKeyPath()), password.toCharArray());
            } catch (IOException e) {
                throw new InvalidPassword();
            }

            try {
                return (PrivateKey) keystore.getKey(keystore.getCertificateAlias(certificate), password.toCharArray());
            } catch (NullPointerException e) {
                throw new InvalidCertificate();
            }
        } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException e) {
            return null;
        }
    }

    public static class InvalidPassword extends Exception {}
    public static class InvalidCertificate extends Exception {}
}
