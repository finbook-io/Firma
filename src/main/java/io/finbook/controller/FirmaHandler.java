package io.finbook.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FirmaHandler {

    protected static void create(File file) {
        try {
            file.createNewFile();
        } catch (IOException ignored) {}
    }

    protected void deleteTheContentOf(File file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class InvalidPassword extends Exception {}
    public static class InvalidCertificate extends Exception {}
}
