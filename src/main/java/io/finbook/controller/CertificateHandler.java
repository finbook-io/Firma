package io.finbook.controller;

import io.finbook.model.FirmaData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
}
