package excel.model;

import java.util.*;

public class ExpressionBuilder {
    public Expression build(String strExpr) {
        if (strExpr.charAt(0) == '=') {
            List<String> tokens = tokenize(strExpr.substring(1));
            System.out.println("Tokens: " + tokens); // Affichage des tokens analysés
            return buildExpression(tokens);
        }
        return null;
    }

    private Expression buildExpression(List<String> tokens) {
        Stack<Expression> values = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                values.push(new NumberExpression(Double.parseDouble(token)));
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

    private List<String> tokenize(String strExpr) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();

        for (char c : strExpr.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else if (isOp(c)) {
                if (number.length() > 0) {
                    tokens.add(number.toString());
                    number.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else if (c == ' ' && number.length() > 0) { // Gestion des espaces
                tokens.add(number.toString());
                number.setLength(0);
            }
        }

        if (number.length() > 0) {
            tokens.add(number.toString());
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
