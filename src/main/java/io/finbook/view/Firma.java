package io.finbook.view;

import io.finbook.controller.CertificateHandler;
import io.finbook.controller.PrivateKeyHandler;
import io.finbook.controller.Signer;
import io.finbook.model.FirmaData;
import io.finbook.model.SignData;

import javax.swing.*;
import java.awt.*;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class Firma extends JFrame {

    private String textToSign;
    private FirmaData firmaData;

    public Firma(String textToSign) {
        this.textToSign = textToSign;
        this.firmaData = new FirmaData();

        CertificateHandler ch = new CertificateHandler(firmaData);
        ch.setCertificatePath();
        X509Certificate certificate = null;
        try {
            certificate = ch.getCertificate();
        } catch (CertificateHandler.InvalidCertificate invalidCertificate) {
            System.out.println("Certificado inválido");
            System.out.println(1);
        }

        PrivateKeyHandler pkh = new PrivateKeyHandler(firmaData, certificate);
        pkh.setPrivateKeyPath();

        setContentPane(getPaneBuilded());
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String password = JOptionPane.showInputDialog(this, "Introduzca su contraseña de la clave privada", "Contraseña", JOptionPane.PLAIN_MESSAGE);
        System.out.println(password);

        PrivateKey pk = null;
        try {
            pk = pkh.getPrivateKey(password);
        } catch (PrivateKeyHandler.InvalidPassword invalidPassword) {
            System.out.println("Contraseña inválida");
        } catch (PrivateKeyHandler.InvalidCertificate invalidCertificate) {
            System.out.println("Certificado inválido");
            System.out.println(2);
        }

        SignData signData = new SignData(pk, certificate, textToSign);
        Signer signer = new Signer(signData);
        signer.sign();
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