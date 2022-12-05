package de.tum.in.ase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Logic {
    private final List<String> history = new ArrayList<>();
    private static final Pattern INPUT_VALIDATION_PATTERN = Pattern.compile("^-?(0|[1-9]\\d*)[\\+x÷%-](0|[1-9]\\d*)$");

    public @NonNull String evaluate(@NonNull String input) {
         
        if (!Logic.INPUT_VALIDATION_PATTERN.matcher(input).matches()) {
            return "INVALID INPUT";
        }
        history.add("Input: " + input + System.lineSeparator());
        // TODO Task 1.1 - 1.5: Implement input handling and output calculation.
        history.add("Result: " + input + System.lineSeparator());
        return input;
    }

    private @NonNull long sum(@NonNull long firstNumber, @NonNull long secondNumber) {
        return firstNumber + secondNumber;
    }

    private @NonNull long sub(@NonNull long firstNumber, @NonNull long secondNumber) {
        return firstNumber - secondNumber;
    }

    private @NonNull long mul(@NonNull long firstNumber, @NonNull long secondNumber) {
        return firstNumber * secondNumber;
    }

    private @NonNull long div(@NonNull long firstNumber, @NonNull long secondNumber) {
        return firstNumber / secondNumber;
    }

    private @NonNull long modulo(@NonNull long firstNumber, @NonNull long secondNumber) {
        return firstNumber % secondNumber;
    }

    public @NonNull List<String> getHistory() {
        return List.copyOf(history);
    }

}
