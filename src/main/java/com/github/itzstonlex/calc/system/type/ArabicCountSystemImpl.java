package com.github.itzstonlex.calc.system.type;

import com.github.itzstonlex.calc.CalcEngine;
import com.github.itzstonlex.calc.system.CountSystemAdapter;
import lombok.NonNull;

import java.util.regex.Pattern;

public final class ArabicCountSystemImpl extends CountSystemAdapter {
    private static final Pattern PATTERN = Pattern.compile("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

    public ArabicCountSystemImpl(@NonNull CalcEngine engine) {
        super(engine);
    }

    @Override
    public Pattern pattern() {
        return ArabicCountSystemImpl.PATTERN;
    }

    @Override
    public boolean validate(@NonNull String input) {
        return ArabicCountSystemImpl.PATTERN.matcher(input).find();
    }

    @Override
    public int parse(@NonNull String value) {
        return Integer.parseInt(value);
    }

}
