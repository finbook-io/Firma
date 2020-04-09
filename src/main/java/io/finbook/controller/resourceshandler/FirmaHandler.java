package io.finbook.controller.resourceshandler;

import java.io.File;
import java.io.IOException;

public abstract class FirmaHandler {

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
