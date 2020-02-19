package io.finbook.view;

public interface Firma {

    void init();
    void setCertificatePath();
    void setPrivateKeyPath();
    void getCertificate();
    void getPrivateKey();
    void signText();
    void returnTextSigned();
    void showMessage(String title, String message, int messageType);

    default void setPaths() {
        setCertificatePath();
        setPrivateKeyPath();
    }

    default void getFiles() {
        getCertificate();
        getPrivateKey();
    }

    default void signAndSave() {
        signText();
        returnTextSigned();
    }
}
