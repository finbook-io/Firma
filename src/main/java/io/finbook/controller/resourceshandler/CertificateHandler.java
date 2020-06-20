package io.finbook.controller.resourceshandler;

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

    private final static String CERTIFICATE_PATH = "/certificate_path.txt";
    private final static File certificatePathFile;

    static {
        certificatePathFile = new File(FirmaHandler.getProjectPath() + CERTIFICATE_PATH);
        create(certificatePathFile);
    }

    public CertificateHandler(FirmaData firmaData, SignData signData) {
        super(firmaData, signData);

        Security.setProperty("crypto.policy", "unlimited");
        try {
            javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        } catch (NoSuchAlgorithmException ignored) {
            ignored.printStackTrace();
        }
        Security.addProvider(new BouncyCastleProvider());
    }

    public void setCertificatePath() {
        firmaData.setCertificatePath(getCertificatePath());
    }

    private String getCertificatePath() {
        String certificatePath = null;

        try {
            certificatePath = fromBufferedReader().readLine();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }

        return certificatePath;
    }

    private BufferedReader fromBufferedReader() {
        try {
            return new BufferedReader(new FileReader(certificatePathFile));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getCertificate() throws InvalidCertificate, FileNotFoundException {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");

            try {
                signData.setCertificate((X509Certificate) certFactory.generateCertificate(new FileInputStream(firmaData.getCertificatePath())));
            } catch (FileNotFoundException | NullPointerException e) {
                e.printStackTrace();
                throw new FileNotFoundException();
            } catch (CertificateException e) {
                e.printStackTrace();
                throw new InvalidCertificate();
            }
        } catch (NoSuchProviderException | CertificateException ignored) {
            ignored.printStackTrace();
        }
    }
}
