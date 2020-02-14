package io.finbook.view;

public interface UserInterface {

    default void interfaceDisplay() {
        updatePanel();
        setUpWindow();
    }
    void updatePanel();
    void setUpWindow();
}
