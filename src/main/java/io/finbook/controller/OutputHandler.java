package io.finbook.controller;

import io.finbook.model.SignData;

import java.io.*;

public class OutputHandler {

    private final SignData signData;
    private final static String OUTPUT_PATH = "text_signed.txt";
    private static File outputData;

    static {
        outputData = new File(OUTPUT_PATH);
        createFile();
    }

    private static void createFile() {
        try {
            outputData.createNewFile();
        } catch (IOException ignored) {}
    }

    public OutputHandler(SignData signData) {
        this.signData = signData;
    }

    public void saveText() {
        deleteTheContentOf(outputData);
        writeSignIn(outputData);
    }

    private void deleteTheContentOf(File outputData) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputData));
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSignIn(File outputData) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputData.getAbsolutePath());
            fileOutputStream.write(signData.getSign());
            fileOutputStream.close();
        } catch (IOException ignored) { }
    }
}