package io.finbook.controller;

import io.finbook.model.FirmaData;

import java.io.*;

public class PrivateKeyHandler {

    private FirmaData firmaData;
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

    public PrivateKeyHandler(FirmaData firmaData) {
        this.firmaData = firmaData;
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
}
