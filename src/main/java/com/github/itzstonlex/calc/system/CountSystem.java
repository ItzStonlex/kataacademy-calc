package com.github.itzstonlex.calc.system;

import com.github.itzstonlex.calc.exception.CalcException;

import java.util.regex.Pattern;

public interface CountSystem {

    Pattern pattern();

    String calc() throws CalcException;

    int parse(String value);

    boolean validate(String example);

    void write(String example) throws CalcException;

    void clear();
}
