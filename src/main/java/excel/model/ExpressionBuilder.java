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
                values.push(new BooleanExpression(Boolean.parseBoolean(token)));
            } else if (token.equalsIgnoreCase("NOT")) { // Handle NOT
                operators.push(token);
            } else if (isOp(token)) {
                // Ensure lower precedence operators are applied later
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    applyOperator(values, operators);
                }
                operators.push(token);
            }
        }

        // Check for trailing operator
        if (!operators.isEmpty() && values.size() < 2) {
            return null; // Return null for invalid expression
        }

        // Apply any remaining unary NOT operators
        while (!operators.isEmpty() && operators.peek().equalsIgnoreCase("NOT")) {
            if (values.isEmpty()) return null; // Prevent Empty Stack Error
            Expression operand = values.pop();
            values.push(new NotExpression(operand));
            operators.pop();
        }

        // Apply remaining binary operators
        while (!operators.isEmpty()) {
            applyOperator(values, operators);
        }

        return values.isEmpty() ? null : values.pop(); // Ensure not empty
    }

    private void applyOperator(Stack<Expression> values, Stack<String> operators) {
        String operator = operators.pop();
        if (operator.equalsIgnoreCase("NOT")) {
            if (values.isEmpty()) return; // Prevent Empty Stack Error
            Expression operand = values.pop();
            values.push(new NotExpression(operand));
        } else {
            if (values.size() < 2) return; // Prevent Empty Stack Error
            Expression right = values.pop();
            Expression left = values.pop();
            values.push(makeOpExpression(operator, left, right));
        }
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
                    // Handle multi-character operators (like <=, >=, !=, or, etc.)
                    if (i + 1 < strExpr.length()) {
                        String twoCharOp = strExpr.substring(i, i + 2);
                        if (twoCharOp.equals("<=") || twoCharOp.equals(">=") || twoCharOp.equals("!=") || twoCharOp.equalsIgnoreCase("OR")) {
                            tokens.add(twoCharOp);
                            i++; // Skip the next character
                            continue;
                        }
                    }
                    if (i + 2 < strExpr.length()) { // ✅ Handle "AND" and NOT
                        String threeCharOp = strExpr.substring(i, i + 3);
                        if (threeCharOp.equalsIgnoreCase("AND") || threeCharOp.equalsIgnoreCase("NOT")) {
                            tokens.add(threeCharOp); // Convert to uppercase for consistency
                            i += 2; // Skip the next two characters
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
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals(">") || s.equals("<") || s.equals("<=") || s.equals(">=") || s.equals("=") || s.equals("!=") || s.equalsIgnoreCase("AND") || s.equalsIgnoreCase("OR") || s.equalsIgnoreCase("NOT");
    }

    private int precedence(String op) {
        return switch (op) {
            case "OR" -> 1;
            case "AND" -> 2;
            case "NOT" -> 3;
            case ">", "<", "<=", ">=", "=", "!=" -> 4;
            case "+", "-" -> 5;
            case "*", "/" -> 6;
            default -> -1;
        };
    }

    private BinaryExpression makeOpExpression(String tokenOp, Expression left, Expression right) {
        return switch (tokenOp.toUpperCase()) {
            case "+" -> new AdditionExpression(left, right);
            case "-" -> new SubstractionExpression(left, right);
            case "*" -> new MultiplicationExpression(left, right);
            case "/" -> new DivisionExpression(left, right);
            case ">" -> new GreaterThanExpression(left, right);
            case "<" -> new LowerThanExpression(left, right);
            case "<=" -> new LessThanOrEqualExpression(left, right);
            case ">=" -> new BiggerThanOrEqualExpression(left, right);
            case "=" -> new EqualExpression(left, right);
            case "!=" -> new DifferentThanExpression(left, right);
            case "AND" -> new AndExpression(left, right);
            case "OR" -> new OrExpression(left, right);
            default -> null;
        };
    }
}