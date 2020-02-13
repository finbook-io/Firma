package io.finbook.view;

import io.finbook.controller.*;
import io.finbook.model.FirmaData;
import io.finbook.model.SignData;

import javax.swing.*;
import java.awt.*;

public class Firma extends JFrame {

    private String textToSign;
    private FirmaData firmaData;
    private SignData signData;

    public Firma(String textToSign) {
        this.textToSign = textToSign;
        this.firmaData = new FirmaData();
        this.signData = new SignData(textToSign);

        cleanOutputFile();
        setPaths();
        interfaceDisplay();
        getFiles();
        signAndSave();
    }

    private void cleanOutputFile() {
        new OutputHandler(signData).deleteContentOfOutputFile();
    }

    private void setPaths() {
        setCertificatePath();
        setPrivateKeyPath();
    }

    private void setCertificatePath() {
        new CertificateHandler(firmaData, signData).setCertificatePath();
    }

    private void setPrivateKeyPath() {
        new PrivateKeyHandler(firmaData, signData, signData.getCertificate()).setPrivateKeyPath();
    }

    private void interfaceDisplay() {
        updateContentPane();
        setUpWindow();
    }

    private void updateContentPane() {
        Container pane = new JPanel();

        pane.setLayout(new GridLayout(3, 2));
        pane.add(new JLabel("Texto a firmar"));
        pane.add(new JLabel(textToSign));
        pane.add(new JLabel("Directorio de la Clave Privada"));
        pane.add(new JLabel(firmaData.getPrivateKeyPath()));
        pane.add(new JLabel("Directorio del Certificado"));
        pane.add(new JLabel(firmaData.getCertificatePath()));

        setContentPane(pane);
    }

    private void setUpWindow() {
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void getFiles() {
        getCertificate();
        getPrivateKey();
    }

    private void getCertificate() {
        try {
            new CertificateHandler(firmaData, signData).getCertificate();
        } catch (FirmaHandler.InvalidCertificate invalidCertificate) {
            System.out.println("El certificado no ha sido cargado correctamente");
        }
    }

    private void getPrivateKey() {
        String password = JOptionPane.showInputDialog(this, "Introduzca su contraseña de la clave privada", "Contraseña", JOptionPane.PLAIN_MESSAGE);

        try {
            new PrivateKeyHandler(firmaData, signData, signData.getCertificate()).getPrivateKey(password);
        } catch (FirmaHandler.InvalidPassword invalidPassword) {
            System.out.println("La contraseña no es correcta");
        } catch (FirmaHandler.InvalidCertificate invalidCertificate) {
            System.out.println("El certificado no corresponde con la clave privada");
        }
    }

    private void signAndSave() {
        signText();
        saveTextSigned();
    }

    private void signText() {
        new Signer(signData).sign();
    }

    private void saveTextSigned() {
        new OutputHandler(signData).saveText();
    }

    public static void main(String[] args) {
        new Firma("Texto de ejemplo");
    }
}