package de.tum.in.ase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
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
        Pattern pattern = Pattern.compile("[\\+x÷%-]");
        Matcher matcher = pattern.matcher(input);
        //
        Matcher m1 = Pattern.compile("(0|[1-9]\\d*)").matcher(input); //firstNum
        Matcher m2 = Pattern.compile("[\\+x÷%-](0|[1-9]\\d*)").matcher(input); //secondNum
        int removeOperator = 1;
        if (matcher.find() && m1.find() && m2.find()) {
            int start1 = m1.start();
            int end1 = m1.end();
            long firstNum = Long.parseLong(input.substring(start1, end1));

            int start2 = m2.start() + removeOperator;
            int end2 = m2.end();
            long secondNum = Long.parseLong(input.substring(start2, end2));

            int start = matcher.start();
            int end = matcher.end();
            String operator = input.substring(start, end);
            switch (operator) {
                case "+": {
                    input = String.valueOf(sum(firstNum, secondNum));
                    history.add("Result: " + input + System.lineSeparator());
                    return input;
                }
                case "-": {
                    input = String.valueOf(sub(firstNum, secondNum));
                    history.add("Result: " + input + System.lineSeparator());

                    return input;
                }
                case "x": {
                    input = String.valueOf(mul(firstNum, secondNum));
                    history.add("Result: " + input + System.lineSeparator());
                    return input;
                }
                case "÷": {
                    if (secondNum != 0) {
                        input = String.valueOf(div(firstNum, secondNum));
                        history.add("Result: " + input + System.lineSeparator());
                        return input;
                    }
                }
                case "%": {
                    if (secondNum != 0) {
                        input = String.valueOf(modulo(firstNum, secondNum));
                        history.add("Result: " + input + System.lineSeparator());
                        return input;
                    } else {
                        throw new ArithmeticException();
                    }
                }
                default: {
                    new ArithmeticException();
                }
            }
        }
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

    public static void main(String[] args) {
        String input = "200÷10";
        Logic l = new Logic();
        System.out.println(l.evaluate(input));
    }

}
