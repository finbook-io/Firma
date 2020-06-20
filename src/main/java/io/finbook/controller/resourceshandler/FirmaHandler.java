package io.finbook.controller.resourceshandler;

import io.finbook.model.FirmaData;
import io.finbook.model.SignData;

import java.io.File;
import java.io.IOException;

public abstract class FirmaHandler {

    protected FirmaData firmaData;
    protected SignData signData;

    public FirmaHandler(FirmaData firmaData, SignData signData) {
        this.firmaData = firmaData;
        this.signData = signData;
    }

    protected static void create(File file) {
        try {
            file.createNewFile();
        } catch (IOException ignored) {}
    }

    public static String getProjectPath() {
        File jar = new File(FirmaHandler.class.getProtectionDomain().getCodeSource().getLocation().toString());
        return jar.getParent().substring(5);
    }

    public static class InvalidPassword extends Exception {}
    public static class InvalidCertificate extends Exception {}
}
