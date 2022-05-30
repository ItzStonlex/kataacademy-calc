package com.github.itzstonlex.calc.exception;

public class CalcException extends Exception {

    public CalcException(String message) {
        super(message);
    }

    public CalcException(String message, Object... replacements) {
        this(String.format(message, replacements));
    }
}
