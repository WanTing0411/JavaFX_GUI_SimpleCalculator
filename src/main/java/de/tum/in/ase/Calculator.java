package de.tum.in.ase;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Calculator extends Application {
    private List<Button> numberButtons;
    private List<Button> operationButtons;
    private List<Button> otherButtons;
    private Label label = new Label();
    private TextArea textArea = new TextArea();
    private Alert alert = new Alert(AlertType.INFORMATION);
    // Use this for logical operations.
    private Logic logic = new Logic();
    // Use to check if some operation is already used.
    private String currentOperation = "";
    private static final int LIMIT = 50;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Display the user input and results.
        label.setAlignment(Pos.CENTER);
        label.wrapTextProperty().set(true);
        label.setStyle("-fx-text-fill: white;" + "-fx-font-size: 15;");

        // Buttons for numbers.
        var zero = new Button("0");
        var one = new Button("1");
        var two = new Button("2");
        var three = new Button("3");
        var four = new Button("4");
        var five = new Button("5");
        var six = new Button("6");
        var seven = new Button("7");
        var eight = new Button("8");
        var nine = new Button("9");

        // Apply styles and add functionality to number buttons.
        numberButtons = List.of(zero, one, two, three, four, five, six, seven, eight, nine);
        applyStyleForButtons(numberButtons);
        addNumberButtonsFunctionality();

        // Buttons for operations.
        var plus = new Button("+");
        var minus = new Button("-");
        var multiplication = new Button("x");
        var division = new Button("÷");
        var modulo = new Button("%");
        var evaluate = new Button("=");

        // Apply styles and functionality to operations buttons.
        operationButtons = List.of(plus, minus, multiplication, division, modulo, evaluate);
        applyStyleForButtons(operationButtons);
        addOperationButtonsFunctionality();

        // Other buttons.
        var placeHolder1 = new Button();
        var placeHolder2 = new Button();
        var operationsHistory = new Button("H");
        var clear = new Button("C");

        // Apply styles and functionality to other buttons.
        otherButtons = List.of(placeHolder1, placeHolder2, operationsHistory, clear);
        applyStyleForButtons(otherButtons);
        addOtherButtonsFunctionality();

        // Container for buttons.
        var gridPane = new GridPane();
        gridPane.add(operationsHistory, 0, 1);
        gridPane.add(clear, 1, 1);
        gridPane.add(modulo, 2, 1);
        gridPane.add(division, 3, 1);
        gridPane.add(seven, 0, 2);
        gridPane.add(eight, 1, 2);
        gridPane.add(nine, 2, 2);
        gridPane.add(plus, 3, 2);
        gridPane.add(four, 0, 3);
        gridPane.add(five, 1, 3);
        gridPane.add(six, 2, 3);
        gridPane.add(minus, 3, 3);
        gridPane.add(one, 0, 4);
        gridPane.add(two, 1, 4);
        gridPane.add(three, 2, 4);
        gridPane.add(multiplication, 3, 4);
        gridPane.add(placeHolder1, 0, 5);
        gridPane.add(zero, 1, 5);
        gridPane.add(placeHolder2, 2, 5);
        gridPane.add(evaluate, 3, 5);
        // Customization
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        // Main initialization.
        var labelContainer = new HBox(label);
        labelContainer.setAlignment(Pos.CENTER);
        labelContainer.setStyle("-fx-background-color: rgb(22,28,32);");

        var mainBox = new VBox(labelContainer, gridPane);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setStyle("-fx-background-color: rgb(30,38,44);");

        var scene = new Scene(mainBox, 225, 350);

        // Setup main stage.
        var streamResource = getClass().getClassLoader().getResourceAsStream("calculator.png");
        if (streamResource != null) {
            primaryStage.getIcons().add(new Image(streamResource));
        }
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Applies style from Buttons.css file to all buttons in the passed list.
     * You can add additional styles or change parameters manually inside for loop.
     *
     * @param buttonsList - list of buttons.
     */
    private void applyStyleForButtons(List<Button> buttonsList) {
        for (Button button : buttonsList) {
            var resource = getClass().getClassLoader().getResource("Buttons.css");
            if (resource != null) {
                button.getStylesheets().add(resource.toString());
            }
            button.setMinWidth(50);
            button.setMinHeight(50);
        }
    }

    /**
     * Creates action events for number buttons.
     */
    private void addNumberButtonsFunctionality() {
        for (int i = 0; i < numberButtons.size(); i++) {
            numberButtons.get(i).setOnAction(getEventNumbers(i));
        }
    }

    /**
     * Creates action events for operation buttons.
     */
    private void addOperationButtonsFunctionality() {
        List<String> symbols = List.of("+", "-", "x", "÷", "%");
        for (int i = 0; i < operationButtons.size() - 1; i++) {
            operationButtons.get(i).setOnAction(getEventOperation(symbols.get(i)));
        }
        // evaluation button.
        operationButtons.get(5).setOnAction(action -> {
            // TODO Task 2.1 : Complete the action event for evaluation button using the `logic` attribute. Pass the text of the label to the `evaluate(String)` method in `Logic` and then replace the label text with the return value of the `evaluate(String)` method. Additionally reset the currentOperation to `""`.
            if (checkMaximumLength()) {
                outOfBoundAlert();
            } else {
                label.setText(logic.evaluate(label.getText()));
                setCurrentOperation("");
            }
        });
    }

    /**
     * Creates action events for other buttons.
     */
    private void addOtherButtonsFunctionality() {
        // clear button
        otherButtons.get(3).setOnAction(action -> label.setText(""));
        // history button
        otherButtons.get(2).setOnAction(action -> {
            // TODO Task 2.2: Add the current `history` from the `Logic`class to the `textArea` attribute in order to display the history on the history window.
            if (checkMaximumLength()) {
                outOfBoundAlert();
            }
            StringBuilder bld = new StringBuilder();
            String textAreaString = "";
            for (String text : logic.getHistory()) {
                bld.append(text + '\n');
                //textArea.appendText(text);
//                System.out.println(textArea.getText());
            }
            //textAreaString = String.format("%s%n", bld.toString());
            textAreaString = bld.toString();
            textArea.setText(textAreaString);

            // TODO: Optional part, Apply custom css to the text area, see line 136 for example.
            // Text area configuration
            textArea.setWrapText(true);
            textArea.setEditable(false);
            textArea.setMinSize(250, 200);
            textArea.setMaxSize(250, 200);

            // Containers
            var box = new VBox(textArea);
            box.setStyle("-fx-background-color: rgb(30,38,44);");
            var scene = new Scene(box);

            // New stage setup
            var stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("History");
            var streamResource = getClass().getClassLoader().getResourceAsStream("calculator.png");
            if (streamResource != null) {
                stage.getIcons().add(new Image(streamResource));
            }
            stage.setResizable(false);
            stage.show();
        });
    }

    /**
     * Helper method.
     * Creates simple event to add current button's number to label.
     *
     * @param index - number of button.
     * @return new ActionEvent for a button.
     */
    private EventHandler<ActionEvent> getEventNumbers(int index) {
        return action -> {
            if (checkMaximumLength()) {
                outOfBoundAlert();
            } else {
                if (label.getText().equals("INVALID INPUT")) {
                    label.setText(String.valueOf(index));
                } else {
                    label.setText(label.getText() + index);
                }
            }
        };
    }

    /**
     * Helper method.
     * Creates simple event to add current button's symbol to label.
     * If some operation was already started it should be evaluated automatically.
     *
     * @param symbol - String symbol.
     * @return new ActionEvent for a button.
     */
    private EventHandler<ActionEvent> getEventOperation(String symbol) {
        return action -> {
            // TODO Task 2.3: Implement the operation button even handler functionality:
            // 1. Check at first whether the text on the label is already a complete expression (two numbers and an operator in between) (e.g. via RegEx). If that is the case, use evaluate(String) to evaluate the expression and place the evaluation result on the label together with the new operator (on the right side of the evaluation result).
            // 2. If 1 is not the case, check whether the text on the label is already a valid number (positive or negative number) (e.g. via RegEx) or whether the the text on the label is "" and symbol parameter is -.
            // 3. If 2 is the case, add a - to the text on the label and (only if the text on the label was not "" before), adjust the currentOperation to - (e.g. via setCurrentOperation(String)).
            // 4. If 2 is not the case the resulting expression will be invalid. Therefore, please set the text on the label to INVALID INPUT and reset the currentOperation to "" (e.g. via setCurrentOperation(String)).

            if (checkMaximumLength()) {
                outOfBoundAlert();
            } else {
                if (label.getText().equals("INVALID INPUT")) {
                    label.setText(symbol);
                } else {
                    label.setText(label.getText() + symbol);
                }
            }

        };
    }

    /**
     * Check if length of current operations string is out of bound.
     * Works according to GUI current size, can be adjusted if size of the elements
     * is changed.
     */
    private boolean checkMaximumLength() {
        return label.getText().length() > LIMIT;
    }

    /**
     * Tells the user that input limit is reached.
     */
    private void outOfBoundAlert() {
        alert.setTitle("Input Limit");
        alert.setHeaderText(null);
        alert.setContentText("You have reached max length of input!");
        alert.showAndWait();
    }

    // Default getters and setters, do not remove.
    public String getCurrentOperation() {
        return currentOperation;
    }

    public void setCurrentOperation(String currentOperation) {
        this.currentOperation = currentOperation;
    }

}
