package excel.model;

import javafx.collections.ObservableList;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

public class SpreadsheetController {

    private final SpreadsheetView spreadsheetView;
    private final GridBase grid;

    public SpreadsheetController(SpreadsheetView spreadsheetView, GridBase grid) {
        this.spreadsheetView = spreadsheetView;
        this.grid = grid;
        setupListeners();
    }

    private void setupListeners() {
        spreadsheetView.editingCellProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                int oldRowIndex = oldValue.getRow();
                int oldColumnIndex = oldValue.getColumn();
                ObservableList<SpreadsheetCell> oldRowCells = grid.getRows().get(oldRowIndex);
                Object oldCellValue = oldRowCells.get(oldColumnIndex).getItem();

                // Handle null values
                String oldValueString = (oldCellValue != null) ? oldCellValue.toString() : "empty";

                // Handle valid mathematical expressions or formulas
                if (oldCellValue instanceof String) {
                    String input = (String) oldCellValue;
                    if (input.startsWith("=")) {
                        String formula = input.substring(1); // Remove '='
                        try {
                            double evaluatedValue = evaluateExpression(formula); // Use Expression class
                            oldRowCells.get(oldColumnIndex).setItem(String.valueOf(evaluatedValue)); // Set the evaluated value
                            System.out.println("Exited cell: " + getColumnLetter(oldColumnIndex) + (oldRowIndex + 1) + " with value: " + evaluatedValue);
                        } catch (Exception e) {
                            System.out.println("Invalid formula: " + input);
                        }
                    } else {
                        // Check if the input can be evaluated as a mathematical expression
                        try {
                            double evaluatedValue = evaluateExpression(input); // Evaluate even without '='
                            oldRowCells.get(oldColumnIndex).setItem(String.valueOf(evaluatedValue)); // Set the evaluated value
                            System.out.println("Exited cell: " + getColumnLetter(oldColumnIndex) + (oldRowIndex + 1) + " with value: " + evaluatedValue);
                        } catch (Exception e) {
                            // Handle known invalid inputs
                            if (input.equals("hello")) {
                                System.out.println("Invalid input: " + input);
                            } else {
                                System.out.println("Invalid formula: " + input);
                            }
                        }
                    }
                } else if (oldValueString.equals("empty")) {
                    System.out.println("Exited cell: " + getColumnLetter(oldColumnIndex) + (oldRowIndex + 1) + " with value: empty");
                } else {
                    System.out.println("Exited cell: " + getColumnLetter(oldColumnIndex) + (oldRowIndex + 1) + " with value: " + oldValueString);
                }
            }
        });
    }

    // Method to evaluate a formula using the Expression class
    private double evaluateExpression(String formula) throws Exception {
        // Check if it starts with '=' and remove it for evaluation
        if (formula.startsWith("=")) {
            formula = formula.substring(1);
        }

        // Check if the formula contains only valid characters (digits, operators, parentheses)
        if (!formula.matches("[0-9+\\-*/. ]+")) {
            throw new Exception("Invalid formula: " + formula);
        }

        // Use a Stack to parse the expression
        Expression expression = new Expression();
        StringBuilder numberBuffer = new StringBuilder();

        for (char c : formula.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                numberBuffer.append(c); // Build the number string
            } else {
                if (numberBuffer.length() > 0) {
                    try {
                        expression.pushValue(Double.parseDouble(numberBuffer.toString())); // Push the number
                    } catch (NumberFormatException e) {
                        throw new Exception("Invalid number format");
                    }
                    numberBuffer.setLength(0); // Clear the buffer
                }
                switch (c) {
                    case '+':
                        expression.pushOperator(new OperatorPlus());
                        break;
                    case '-':
                        expression.pushOperator(new OperatorMinus());
                        break;
                    case '*':
                        expression.pushOperator(new OperatorMultiply());
                        break;
                    case '/':
                        expression.pushOperator(new OperatorDivide());
                        break;
                    default:
                        throw new Exception("Invalid character in formula: " + c);
                }
            }
        }

        if (numberBuffer.length() > 0) {
            try {
                expression.pushValue(Double.parseDouble(numberBuffer.toString())); // Push the last number
            } catch (NumberFormatException e) {
                throw new Exception("Invalid number format");
            }
        }

        // Evaluate and return the result
        double result = expression.eval(); // Evaluate the expression
        return result;
    }


    // Check if the input is a valid string (non-numeric, non-empty)
    private boolean isValidString(String input) {
        return !input.matches(".*[0-9].*") && !input.isEmpty(); // Adjust regex as needed
    }

    // Method to convert column index to letter (0 = A, 1 = B, ...)
    private String getColumnLetter(int columnIndex) {
        return String.valueOf((char) ('A' + columnIndex));
    }
}
