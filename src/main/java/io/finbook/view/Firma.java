package io.finbook.view;

import io.finbook.controller.*;
import io.finbook.model.FirmaData;
import io.finbook.model.SignData;

import javax.swing.*;
import java.awt.*;

public class Firma extends JFrame {

    private String textToSign;
    private FirmaData firmaData;

    public Firma(String textToSign) {
        this.textToSign = textToSign;
        this.firmaData = new FirmaData();
        SignData signData = new SignData(textToSign);

        CertificateHandler ch = new CertificateHandler(firmaData, signData);
        ch.setCertificatePath();
        try {
            ch.getCertificate();
        } catch (FirmaHandler.InvalidCertificate invalidCertificate) {
            System.out.println("Certificado inválido");
            System.out.println(1);
        }

        PrivateKeyHandler pkh = new PrivateKeyHandler(firmaData, signData, signData.getCertificate());
        pkh.setPrivateKeyPath();

        setContentPane(getPaneBuilded());
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String password = JOptionPane.showInputDialog(this, "Introduzca su contraseña de la clave privada", "Contraseña", JOptionPane.PLAIN_MESSAGE);
        System.out.println(password);

        try {
            pkh.getPrivateKey(password);
        } catch (FirmaHandler.InvalidPassword invalidPassword) {
            System.out.println("Contraseña inválida");
        } catch (FirmaHandler.InvalidCertificate invalidCertificate) {
            System.out.println("Certificado inválido");
            System.out.println(2);
        }

        Signer signer = new Signer(signData);
        signer.sign();

        OutputHandler outputHandler = new OutputHandler(signData);
        outputHandler.saveText();
    }

    private Container getPaneBuilded() {
        Container pane = new JPanel();

        pane.setLayout(new GridLayout(3, 2));
        pane.add(new JLabel("Texto a firmar"));
        pane.add(new JLabel(textToSign));
        pane.add(new JLabel("Directorio de la Clave Privada"));
        pane.add(new JLabel(firmaData.getPrivateKeyPath()));
        pane.add(new JLabel("Directorio del Certificado"));
        pane.add(new JLabel(firmaData.getCertificatePath()));

        return pane;
    }

    public static void main(String[] args) {
        new Firma("Texto de ejemplo");
    }
}