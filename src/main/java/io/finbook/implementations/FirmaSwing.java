package io.finbook.implementations;

import io.finbook.controller.*;
import io.finbook.model.FirmaData;
import io.finbook.model.SignData;
import io.finbook.view.Firma;
import io.finbook.view.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class FirmaSwing extends JFrame implements UserInterface, Firma {

    private String textToSign;
    private FirmaData firmaData;
    private SignData signData;

    public FirmaSwing(String textToSign) {
        this.textToSign = textToSign;
        this.firmaData = new FirmaData();
        this.signData = new SignData(textToSign);
    }

    @Override
    public void init() {
        cleanOutputFile();
        setPaths();
        interfaceDisplay();
        getFiles();
        signAndSave();

        showMessage("Firma completada", "La firma ha sido completada correctamente", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void cleanOutputFile() {
        new OutputHandler(signData).deleteContentOfOutputFile();
    }

    @Override
    public void setCertificatePath() {
        new CertificateHandler(firmaData, signData).setCertificatePath();
    }

    @Override
    public void setPrivateKeyPath() {
        new PrivateKeyHandler(firmaData, signData, signData.getCertificate()).setPrivateKeyPath();
    }

    @Override
    public void updatePanel() {
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

    @Override
    public void setUpWindow() {
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void getCertificate() {
        try {
            new CertificateHandler(firmaData, signData).getCertificate();
        } catch (FirmaHandler.InvalidCertificate invalidCertificate) {
            showMessage("Certificado no válido", "Ha ocurrido algún error al cargar el certificado", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
            showMessage("Certificado no encontrado", "El certificado no ha sido encontrado, o no ha sido especificado", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void getPrivateKey() {
        String password = JOptionPane.showInputDialog(this, "Introduzca su contraseña de la clave privada", "Contraseña", JOptionPane.PLAIN_MESSAGE);

        try {
            new PrivateKeyHandler(firmaData, signData, signData.getCertificate()).getPrivateKey(password);
        } catch (FirmaHandler.InvalidPassword invalidPassword) {
            showMessage("Contraseña no válida", "La contraseña introducida no corresponde con la de la clave privada", JOptionPane.ERROR_MESSAGE);
        } catch (FirmaHandler.InvalidCertificate invalidCertificate) {
            showMessage("Certificado no válido", "El certificado no corresponde al asociado con la clave privada", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
            showMessage("Clave privada no encontrada", "La clave privada no ha sido encontrada, o no ha sido especificada", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void signText() {
        new Signer(signData).sign();
    }

    @Override
    public void saveTextSigned() {
        new OutputHandler(signData).saveText();
    }

    @Override
    public void showMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
        System.exit(0);
    }
}