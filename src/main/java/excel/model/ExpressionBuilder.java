package excel.model;

import java.util.*;

public class ExpressionBuilder {
    private final SpreadsheetModel spreadsheet;

    public ExpressionBuilder(SpreadsheetModel spreadsheet) {
        this.spreadsheet = spreadsheet;
    }

    public Expression build(String strExpr) {
        if (strExpr.isEmpty()) {
            return null;
        }
        if (strExpr.charAt(0) == '=') {
            List<String> tokens = tokenize(strExpr.substring(1));
            System.out.println("Tokens: " + tokens); // Debugging
            return buildExpression(tokens);
        }
        if (isNumber(strExpr)) {
            double value = Double.parseDouble(strExpr);
            return new NumberExpression(value);
        }
        return null;
    }

    private Expression buildExpression(List<String> tokens) {
        Stack<Expression> values = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                values.push(new NumberExpression(Double.parseDouble(token)));
            } else if (isReference(token)) {
                values.push(new ReferenceExpression(token, spreadsheet));
            } else if (isBoolean(token)) {
                // Handle boolean literals
                values.push(new BooleanExpression(Boolean.parseBoolean(token)));
            } else if (isOp(token)) {
                // Ensure lower precedence operators are applied later
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    if (values.size() < 2) return null; // Prevent Empty Stack Error
                    Expression right = values.pop();
                    Expression left = values.pop();
                    values.push(makeOpExpression(operators.pop(), left, right));
                }
                operators.push(token);
            }
        }

        // ⬇️ FIX: Ensure all operators are applied in correct order
        while (!operators.isEmpty()) {
            if (values.size() < 2) return null; // Prevent Empty Stack Error
            Expression right = values.pop();
            Expression left = values.pop();
            values.push(makeOpExpression(operators.pop(), left, right));
        }

        return values.isEmpty() ? null : values.pop(); // Ensure not empty
    }

    // Add this method
    private boolean isBoolean(String s) {
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }

    private boolean isReference(String token) {
        return token.matches("^[A-Z]+[0-9]+$"); // Example: A1, B2, Z99
    }


    private List<String> tokenize(String strExpr) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < strExpr.length(); i++) {
            char c = strExpr.charAt(i);

            // Handle letters or digits (part of variables or numbers)
            if (Character.isLetterOrDigit(c)) {
                currentToken.append(c);
            } else {
                // When we encounter a space or an operator
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }

                if (!Character.isWhitespace(c)) {
                    // Handle multi-character operators (like <=, >=, ==, etc.)
                    if (i + 1 < strExpr.length()) {
                        String twoCharOp = strExpr.substring(i, i + 2);
                        if (twoCharOp.equals("<=") || twoCharOp.equals(">=")) {
                            tokens.add(twoCharOp);
                            i++; // Skip the next character
                            continue;
                        }
                    }
                    // Add single-character operators
                    tokens.add(String.valueOf(c));
                }
            }
        }

        // Add the last token if there's any remaining
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }



    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOp(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals(">") || s.equals("<") || s.equals("<=") || s.equals(">=") || s.equals("=");
    }

    private int precedence(String op) {
        return switch (op) {
            case ">", "<", "<=", ">=", "=" -> 1;   // Lower precedence (evaluated last)
            case "+", "-" -> 2;
            case "*", "/" -> 3;
            default -> -1;
        };
    }

    private BinaryExpression makeOpExpression(String tokenOp, Expression left, Expression right) {
        return switch (tokenOp) {
            case "+" -> new AdditionExpression(left, right);
            case "-" -> new SubstractionExpression(left, right);
            case "*" -> new MultiplicationExpression(left, right);
            case "/" -> new DivisionExpression(left, right);
            case ">" -> new GreaterThanExpression(left, right);
            case "<" -> new LowerThanExpression(left, right);
            case "<=" -> new LessThanOrEqualExpression(left, right);
            case ">=" -> new BiggerThanOrEqualExpression(left, right);
            case "=" -> new EqualExpression(left, right);
            default -> null;
        };
    }
}