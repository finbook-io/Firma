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
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile.getAbsolutePath());
            fileOutputStream.write(signData.getSign());
            fileOutputStream.close();
        } catch (IOException | NullPointerException ignored) { }
    }

    public void deleteContentOfOutputFile() {
        deleteTheContentOf(outputFile);
    }
}