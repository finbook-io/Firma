package io.finbook.controller;

import io.finbook.model.FirmaData;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class PrivateKeyHandler {

    private FirmaData firmaData;
    private X509Certificate certificate;
    private static File privateKeyData;

    static {
        privateKeyData = new File("private_key_path.txt");
        createFile();
    }

    private static void createFile() {
        try {
            privateKeyData.createNewFile();
        } catch (IOException ignored) {}
    }

    public PrivateKeyHandler(FirmaData firmaData, X509Certificate certificate) {
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

    public PrivateKey getPrivateKey(String password) throws InvalidPassword {
        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            try {
                keystore.load(new FileInputStream(firmaData.getPrivateKeyPath()), password.toCharArray());
            } catch (IOException e) {
                throw new InvalidPassword();
            }

            return (PrivateKey) keystore.getKey(keystore.getCertificateAlias(certificate), password.toCharArray());
        } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException e) {
            return null;
        }
    }

    public class InvalidPassword extends Exception {

    }
}
