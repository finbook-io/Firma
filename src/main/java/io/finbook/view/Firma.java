package io.finbook.view;

import javax.swing.*;
import java.awt.*;

public class Firma extends JFrame {

    private String textToSign;

    public Firma(String textToSign) {
        this.textToSign = textToSign;
        
        setContentPane(getPaneBuilded());
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String password = JOptionPane.showInputDialog(this, "Introduzca su contraseña de la clave privada", "Contraseña", JOptionPane.PLAIN_MESSAGE);
        System.out.println(password);
    }

    private Container getPaneBuilded() {
        Container pane = new JPanel();

        pane.setLayout(new GridLayout(3, 2));
        pane.add(new JLabel("Texto a firmar"));
        pane.add(new JLabel(textToSign));
        pane.add(new JLabel("Directorio de la Clave Privada"));
        pane.add(new JLabel("Directorio de ejemplo"));
        pane.add(new JLabel("Directorio del Certificado"));
        pane.add(new JLabel("Directorio de ejemplo"));

        return pane;
    }

    public static void main(String[] args) {
        new Firma("Texto a firmar");
    }
}