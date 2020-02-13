package io.finbook.controller;

import io.finbook.model.SignData;

import java.io.*;

public class OutputHandler extends FirmaHandler {

    private final SignData signData;
    private final static String OUTPUT_PATH = "text_signed.txt";
    private final static File outputFile;

    static {
        outputFile = new File(OUTPUT_PATH);
        create(outputFile);
    }

    public OutputHandler(SignData signData) {
        this.signData = signData;
    }

    public void saveText() {
        deleteTheContentOf(outputFile);
        writeSignIn(outputFile);
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