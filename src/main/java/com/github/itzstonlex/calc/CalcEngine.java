package com.github.itzstonlex.calc;

import com.github.itzstonlex.calc.system.CountSystem;
import com.github.itzstonlex.calc.exception.CalcException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@EqualsAndHashCode
public final class CalcEngine {

    private static final int MAX_NUMBER_VALUE = 10;
    private final List<CountSystem> COUNT_SYSTEM_LIST = new ArrayList<>();

    public void register(@NonNull CountSystem system) {
        COUNT_SYSTEM_LIST.add(system);
    }

    public CountSystem detect(@NonNull String example)
    throws CalcException {

        boolean multi = false;

        CountSystem detected = null;
        for (CountSystem countSystem : COUNT_SYSTEM_LIST) {

            if (countSystem.validate(example)) {
                detected = detected != null ? null : countSystem;

                multi = (detected == null);
            }
        }

        if (detected != null) {
            return detected;
        }

        throw new CalcException(multi ? "input must be have one count-system only" : "unknown counter system");
    }

    public int calc(@NonNull String numberPattern,
                    @NonNull String input,
                    @NonNull Function<String, Integer> numberParser)
    throws CalcException {

        int operatorIndex = 1;
        List<String> preFilteredList = new ArrayList<>( Arrays.asList(input.split(numberPattern)) );

        // checks numbers values
        for (String value : preFilteredList) {
            try {
                int number = numberParser.apply(value);

                if (number > MAX_NUMBER_VALUE) {
                    throw new CalcException("%d > %d", number, MAX_NUMBER_VALUE);
                }
            }
            catch (NumberFormatException ignored) {
                // ignored exception
            }
        }

        // checks if any more operators are left
        while (preFilteredList.contains("*") || (preFilteredList.contains("/")) || (preFilteredList.contains("+"))
                || (preFilteredList.contains("-"))) {

            // get the two numbers used in the calculation
            boolean replace = false;

            int numberOne = numberParser.apply(preFilteredList.get(operatorIndex - 1));
            int numberTwo = numberParser.apply(preFilteredList.get(operatorIndex + 1));

            // Calculates everything
            if (preFilteredList.contains("*")) {
                if (preFilteredList.get(operatorIndex).equals("*")) {

                    preFilteredList.set(operatorIndex + 1, (numberOne * numberTwo) + "");
                    replace = true;
                }

            } else if (preFilteredList.contains("/")) {
                if (preFilteredList.get(operatorIndex).equals("/")) {

                    preFilteredList.set(operatorIndex + 1, (numberOne / numberTwo) + "");
                    replace = true;
                }
            }
            else if (preFilteredList.contains("+")) {
                if (preFilteredList.get(operatorIndex).equals("+")) {

                    preFilteredList.set(operatorIndex + 1, (numberOne + numberTwo) + "");
                    replace = true;
                }
            }
            else if (preFilteredList.contains("-")) {
                if (preFilteredList.get(operatorIndex).equals("-")) {

                    preFilteredList.set(operatorIndex + 1, (numberOne - numberTwo) + "");
                    replace = true;
                }
            }

            // It only removes the lines in the List once it calculated them
            if (replace) {
                preFilteredList.remove(operatorIndex - 1);
                preFilteredList.remove(operatorIndex - 1);
            }
            else {
                operatorIndex = operatorIndex + 2;
            }

            // once it
            if (operatorIndex >= preFilteredList.size()) {
                operatorIndex = 1;
            }
        }

        System.out.println(preFilteredList.get(0));
        return Integer.parseInt(preFilteredList.get(0));
    }
}
