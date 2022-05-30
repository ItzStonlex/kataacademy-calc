package com.github.itzstonlex.calc.system.type;

import com.github.itzstonlex.calc.CalcEngine;
import com.github.itzstonlex.calc.exception.CalcException;
import com.github.itzstonlex.calc.system.CountSystemAdapter;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class GreekCountSystemImpl extends CountSystemAdapter {
    private static final Pattern PATTERN = Pattern.compile("(?<=([IVXLCDM]))|(?=([IVXLCDM]))");

    public GreekCountSystemImpl(@NonNull CalcEngine engine) {
        super(engine);
    }

    @Override
    public Pattern pattern() {
        return GreekCountSystemImpl.PATTERN;
    }

    @Override
    public boolean validate(@NonNull String input) {
        return GreekCountSystemImpl.PATTERN.matcher(input.toUpperCase()).find();
    }

    @Override
    public int parse(@NonNull String value) {
        System.out.println("greek parse: " + value);

        String currentNumeral = value.toUpperCase();
        List<Numeral> numerals = Numeral.getReverseSortedValues();

        int result = 0, indexCounter = 0;

        while ((currentNumeral.length() > 0) && (indexCounter < numerals.size())) {
            Numeral symbol = numerals.get(indexCounter);

            if (currentNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();

                currentNumeral = currentNumeral.substring(symbol.name().length());

            } else indexCounter++;
        }

        if (currentNumeral.length() > 0) {
            throw new IllegalArgumentException(value + " cannot be parse to a Greek Numeral");
        }

        return result;
    }

    @Override
    public String calc()
    throws CalcException {

        int calcResult = Integer.parseInt(super.calc());
        if (calcResult <= 0 || calcResult > 4000) {
            throw new CalcException("%d is not in range (0,4000]", calcResult);
        }

        List<Numeral> numerals = Numeral.getReverseSortedValues();
        StringBuilder builder = new StringBuilder();

        int indexCounter = 0;

        while ((calcResult > 0) && (indexCounter < numerals.size())) {
            Numeral currentSymbol = numerals.get(indexCounter);

            if (currentSymbol.getValue() <= calcResult) {
                builder.append(currentSymbol.name());

                calcResult -= currentSymbol.getValue();

            } else indexCounter++;
        }

        return builder.toString();
    }

    @Getter
    @RequiredArgsConstructor
    public enum Numeral {

        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);
        ;

        public static List<Numeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparingInt((Numeral numeral) -> numeral.value).reversed())
                    .collect(Collectors.toList());
        }

        private final int value;
    }

}
