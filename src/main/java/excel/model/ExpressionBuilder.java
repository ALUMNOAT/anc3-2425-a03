package excel.model;

import java.util.*;

public class ExpressionBuilder {
    private final SpreadsheetModel spreadsheet;

    public ExpressionBuilder(SpreadsheetModel spreadsheet) {
        this.spreadsheet = spreadsheet;
    }

    public Expression build(String strExpr) {
        if (strExpr.isEmpty()){
            return null;
        }
        if (strExpr.charAt(0) == '=') {
            List<String> tokens = tokenize(strExpr.substring(1));
            System.out.println("Tokens: " + tokens); // Affichage des tokens analysés
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
            } else if (isReference(token)) { // NEW: Handle references like A1, B2
                values.push(new ReferenceExpression(token, spreadsheet));
            } else if (isOp(token)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    Expression right = values.pop();
                    Expression left = values.pop();
                    values.push(makeOpExpression(operators.pop(), left, right));
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            Expression right = values.pop();
            Expression left = values.pop();
            values.push(makeOpExpression(operators.pop(), left, right));
        }

        return values.pop();
    }

    private boolean isReference(String token) {
        return token.matches("^[A-Z]+[0-9]+$"); // Example: A1, B2, Z99
    }


    private List<String> tokenize(String strExpr) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (char c : strExpr.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                currentToken.append(c);
            } else {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                if (!Character.isWhitespace(c)) {
                    tokens.add(String.valueOf(c));
                }
            }
        }

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

    private boolean isOp(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean isOp(String s) {
        return s.length() == 1 && isOp(s.charAt(0));
    }

    private int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> -1;
        };
    }

    private BinaryExpression makeOpExpression(String tokenOp, Expression left, Expression right) {
        return switch (tokenOp) {
            case "+" -> new AdditionExpression(left, right);
            case "-" -> new SubstractionExpression(left, right);
            case "*" -> new MultiplicationExpression(left, right);
            case "/" -> new DivisionExpression(left, right);
            default -> null;
        };
    }
}
