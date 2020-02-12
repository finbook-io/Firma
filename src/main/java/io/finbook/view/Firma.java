package io.finbook.view;

import io.finbook.controller.PrivateKeyHandler;
import io.finbook.model.FirmaData;

import javax.swing.*;
import java.awt.*;
import java.security.PrivateKey;

public class Firma extends JFrame {

    private String textToSign;
    private FirmaData firmaData;

    public Firma(String textToSign) {
        this.textToSign = textToSign;
        this.firmaData = new FirmaData();

        PrivateKeyHandler pkh = new PrivateKeyHandler(firmaData, null);
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
        }
        System.out.println(pk);
    }

    private Container getPaneBuilded() {
        Container pane = new JPanel();

        pane.setLayout(new GridLayout(3, 2));
        pane.add(new JLabel("Texto a firmar"));
        pane.add(new JLabel(textToSign));
        pane.add(new JLabel("Directorio de la Clave Privada"));
        pane.add(new JLabel(firmaData.getPrivateKeyPath()));
        pane.add(new JLabel("Directorio del Certificado"));
        pane.add(new JLabel("Directorio de ejemplo"));

        return pane;
    }

    public static void main(String[] args) {
        new Firma("Texto de ejemplo");
    }
}