package com.github.itzstonlex.calc;

import com.github.itzstonlex.calc.exception.CalcException;
import com.github.itzstonlex.calc.system.CountSystem;
import com.github.itzstonlex.calc.system.type.ArabicCountSystemImpl;
import com.github.itzstonlex.calc.system.type.GreekCountSystemImpl;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Scanner;

public class CalcStarter {

    public static final CalcEngine ENGINE = new CalcEngine();

    @SneakyThrows
    public static void main(String[] args) {
        // System.out.println(Arrays.toString("I+I+V*IX".split("(?<=([IVXLCDM]))|(?=([IVXLCDM]))")));

        ENGINE.register(new ArabicCountSystemImpl(ENGINE));
        ENGINE.register(new GreekCountSystemImpl(ENGINE));
        // ...registering other counter systems

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            execute(scanner);
        }
    }

    private static void execute(Scanner scanner)
    throws CalcException {

        String input = scanner.nextLine().replace(" ", "");

        CountSystem system = ENGINE.detect(input);
        system.write(input);

        // Print a calc result.
        System.out.printf("result: %s = %s%n", input, system.calc());
        system.clear();
    }

}
