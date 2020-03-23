package io.finbook.controller.output;

import io.finbook.controller.resourceshandler.FirmaHandler;
import io.finbook.controller.output.OutputHandler;
import io.finbook.model.SignData;

import java.io.*;

public class FileOutputHandler extends FirmaHandler implements OutputHandler {

    private final SignData signData;
    private final static String OUTPUT_PATH = "text_signed.txt";
    private final File outputFile;

    public FileOutputHandler(SignData signData) {
        this.signData = signData;
        this.outputFile = new File(OUTPUT_PATH);
        initOutput();
    }

    @Override
    public void initOutput() {
        create(outputFile);
        deleteTheContentOf(outputFile);
    }

    @Override
    public void returnTextSigned() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile.getAbsolutePath());
            fileOutputStream.write(signData.getSign());
            fileOutputStream.close();
        } catch (IOException | NullPointerException ignored) {
            ignored.printStackTrace();
        }
    }

    private void deleteTheContentOf(File file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}