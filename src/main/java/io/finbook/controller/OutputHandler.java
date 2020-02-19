package io.finbook.controller;

public interface OutputHandler {

    default void initOutput() {}
    void returnTextSigned();
}
