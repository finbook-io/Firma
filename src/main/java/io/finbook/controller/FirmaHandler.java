package io.finbook.controller;

import java.io.File;
import java.io.IOException;

public abstract class FirmaHandler {

    protected static void create(File file) {
        try {
            file.createNewFile();
        } catch (IOException ignored) {}
    }

    public static class InvalidPassword extends Exception {}
    public static class InvalidCertificate extends Exception {}
}
