package io.finbook.controller.output;

public interface OutputHandler {
    default void initOutput() {}
    void returnTextSigned();
}
