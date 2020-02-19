package io.finbook.controller;

import io.finbook.model.FirmaData;
import io.finbook.model.SignData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class PrivateKeyHandler extends FirmaHandler {

    private FirmaData firmaData;
    private SignData signData;
    private X509Certificate certificate;
    private final static String PRIVATE_KEY_PATH = "private_key_path.txt";
    private final static File privateKeyPathFile;

    static {
        privateKeyPathFile = new File(PRIVATE_KEY_PATH);
        create(privateKeyPathFile);
    }

    public PrivateKeyHandler(FirmaData firmaData, SignData signData, X509Certificate certificate) {
        Security.setProperty("crypto.policy", "unlimited");
        try {
            javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        } catch (NoSuchAlgorithmException ignored) {
            ignored.printStackTrace();
        }
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
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }

        return privateKeyPath;
    }

    private BufferedReader fromBufferedReader() {
        try {
            return new BufferedReader(new FileReader(privateKeyPathFile));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getPrivateKey(String password) throws InvalidPassword, InvalidCertificate, FileNotFoundException {
        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            try {
                keystore.load(new FileInputStream(firmaData.getPrivateKeyPath()), password.toCharArray());
            } catch (FileNotFoundException | NullPointerException e) {
                e.printStackTrace();
                throw new FileNotFoundException();
            } catch (IOException e) {
                e.printStackTrace();
                throw new InvalidPassword();
            }

            try {
                signData.setPrivateKey((PrivateKey) keystore.getKey(keystore.getCertificateAlias(certificate), password.toCharArray()));
            } catch (NullPointerException e) {
                e.printStackTrace();
                throw new InvalidCertificate();
            }
        } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException ignored) {
            ignored.printStackTrace();
        }
    }
}
