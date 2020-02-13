package io.finbook.controller;

import io.finbook.model.FirmaData;
import io.finbook.model.SignData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class PrivateKeyHandler {

    private FirmaData firmaData;
    private SignData signData;
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

    public PrivateKeyHandler(FirmaData firmaData, SignData signData, X509Certificate certificate) {
        Security.setProperty("crypto.policy", "unlimited");
        try {
            javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        } catch (NoSuchAlgorithmException ignored) {}
        Security.addProvider(new BouncyCastleProvider());

        this.firmaData = firmaData;
        this.signData = signData;
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

    public void getPrivateKey(String password) throws InvalidPassword, InvalidCertificate {
        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            try {
                keystore.load(new FileInputStream(firmaData.getPrivateKeyPath()), password.toCharArray());
            } catch (IOException e) {
                throw new InvalidPassword();
            }

            try {
                signData.setPrivateKey((PrivateKey) keystore.getKey(keystore.getCertificateAlias(certificate), password.toCharArray()));
            } catch (NullPointerException e) {
                throw new InvalidCertificate();
            }
        } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException ignored) {}
    }

    public static class InvalidPassword extends Exception {}
    public static class InvalidCertificate extends Exception {}
}
