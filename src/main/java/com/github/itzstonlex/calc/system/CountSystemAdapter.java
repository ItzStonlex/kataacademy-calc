package com.github.itzstonlex.calc.system;

import com.github.itzstonlex.calc.CalcEngine;
import com.github.itzstonlex.calc.exception.CalcException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class CountSystemAdapter implements CountSystem {

    @NonFinal
    String inputBuf;

    @ToString.Exclude
    @NonNull
    CalcEngine engine;

    @Override
    public String calc()
    throws CalcException {

        if (inputBuf == null) {
            throw new CalcException("input equals null, use write(String) for init input value");
        }

        int calcResult = engine.calc(this.pattern().pattern(), inputBuf, this::parse);
        return String.valueOf(calcResult);
    }

    @Override
    public void write(@NonFinal String input)
    throws CalcException {

        if (!this.pattern().matcher(input).find()) {
            throw new CalcException("incorrect input format");
        }

        inputBuf = input;
    }

    @Override
    public void clear() {
        if (inputBuf != null) {
            inputBuf = null;
        }
    }

}
