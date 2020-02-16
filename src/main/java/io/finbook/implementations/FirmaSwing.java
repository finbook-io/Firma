package io.finbook.implementations;

import io.finbook.controller.*;
import io.finbook.model.FirmaData;
import io.finbook.model.SignData;
import io.finbook.view.Firma;
import io.finbook.view.UserInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FirmaSwing extends JFrame implements UserInterface, Firma {

    private String textToSign;
    private FirmaData firmaData;
    private SignData signData;
    private FileOutputHandler outputHandler;
    private boolean mustBeSigned;

    public FirmaSwing(String textToSign, boolean mustBeSigned) {
        this.textToSign = textToSign;
        this.firmaData = new FirmaData();
        this.signData = new SignData(textToSign);
        this.outputHandler = new FileOutputHandler(signData);
        this.mustBeSigned = mustBeSigned;
    }

    @Override
    public void init() {
        setPaths();
        interfaceDisplay();
        if(mustBeSigned) {
            getFiles();
            signAndSave();
            showMessage("Firma completada", "La firma ha sido completada correctamente", JOptionPane.INFORMATION_MESSAGE);
        }
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
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        pane.add(firmaLogo());
        pane.add(dataPane());

        setContentPane(pane);
    }

    private Component firmaLogo() {
        Container imagePane = new JPanel();

        imagePane.add(new FirmaLogo());

        return imagePane;
    }

    private Component dataPane() {
        Container dataPane = new JPanel();
        dataPane.setLayout(new GridLayout(3, 2));

        dataPane.add(new JLabel("Texto a firmar"));
        dataPane.add(new JLabel(textToSign));
        dataPane.add(new JLabel("Directorio de la Clave Privada"));
        dataPane.add(new JLabel(firmaData.getPrivateKeyPath()));
        dataPane.add(new JLabel("Directorio del Certificado"));
        dataPane.add(new JLabel(firmaData.getCertificatePath()));

        return dataPane;
    }

    @Override
    public void setUpWindow() {
        setTitle("FinBook - Firma");
        try {
            setIconImage(ImageIO.read(new File("resources/FinBookFirmaWhite.png")));
        } catch (IOException ignored) {}

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
        try {
            new PrivateKeyHandler(firmaData, signData, signData.getCertificate()).getPrivateKey(password());
        } catch (FirmaHandler.InvalidPassword invalidPassword) {
            showMessage("Contraseña no válida", "La contraseña introducida no corresponde con la de la clave privada", JOptionPane.ERROR_MESSAGE);
        } catch (FirmaHandler.InvalidCertificate invalidCertificate) {
            showMessage("Certificado no válido", "El certificado no corresponde al asociado con la clave privada", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
            showMessage("Clave privada no encontrada", "La clave privada no ha sido encontrada, o no ha sido especificada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String password(){
        JOptionPane optionPane = new JOptionPane();
        JPasswordField passwordTextField = new JPasswordField("");
        optionPane.setMessage(new Object[] {"Contraseña: ", passwordTextField});
        optionPane.setMessageType(JOptionPane.PLAIN_MESSAGE);
        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(this, "Contraseña de su clave privada");
        dialog.setVisible(true);
        String password = String.valueOf(passwordTextField.getPassword());
        if(!password.equals("")) {
            return password;
        } else {
            System.exit(0);
            return null;
        }
    }

    @Override
    public void signText() {
        new Signer(signData).sign();
    }

    @Override
    public void returnTextSigned() {
        outputHandler.returnTextSigned();
    }

    @Override
    public void showMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
        System.exit(0);
    }

    private class FirmaLogo extends JPanel{

        private BufferedImage image;

        public FirmaLogo() {
            try {
                image = ImageIO.read(new File("resources/FinBookFirma.png"));
            } catch (IOException ignored) {}
            this.setMinimumSize(new Dimension(109, 50));
            this.setPreferredSize(new Dimension(109, 50));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(image != null) {
                g.drawImage(image.getScaledInstance(109, 50, 0), 0, 0, this);
            }
        }

    }
}