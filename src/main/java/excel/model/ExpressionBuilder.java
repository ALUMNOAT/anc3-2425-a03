package excel.model;

import java.util.ArrayList;
import java.util.List;

public class ExpressionBuilder {
    public Expression build(String strExpr) {
        if (strExpr.charAt(0) == '=') {
            List<String> tokens = tokenize(strExpr.substring(1));
            System.out.println(tokens);
            return buildExpression(tokens);
        }
        return null;
    }

    private Expression buildExpression(List<String> tokens) {
        // Chercher l'opérateur le plus à droite
        int idxOp = findLastOperator(tokens);

        if (idxOp != -1) {
            String op = tokens.get(idxOp);
            // Séparer les tokens en deux parties
            List<String> leftTokens = new ArrayList<>(tokens.subList(0, idxOp));
            List<String> rightTokens = new ArrayList<>(tokens.subList(idxOp + 1, tokens.size()));

            // Construire récursivement les sous-expressions
            Expression left = buildExpression(leftTokens);
            Expression right = buildExpression(rightTokens);

            return makeOpExpression(op, left, right);
        }

        // Si plus d'opérateurs, c'est un nombre
        if (tokens.size() == 1) {
            return new NumberExpression(Double.parseDouble(tokens.get(0)));
        }

        return null;
    }

    private int findLastOperator(List<String> tokens) {
        // On cherche de droite à gauche pour respecter l'associativité
        for (int i = tokens.size() - 1; i >= 0; i--) {
            String token = tokens.get(i);
            if (isOp(token)) {
                return i;
            }
        }
        return -1;
    }

    public List<String> tokenize(String strExpr) {
        List<String> tokens = new ArrayList<>();
        String number = "";
        for (int i = 0; i < strExpr.length(); ++i) {
            char c = strExpr.charAt(i);
            if (Character.isDigit(c)) {
                number += c;
            } else if (isOp(c) && !number.isEmpty()) {
                tokens.add(number);
                number = "";
                tokens.add(""+c);
            }
        }
        if (!number.isEmpty())
            tokens.add(number);
        return tokens;
    }

    public boolean isOp(char c) {
        return c == '+' || c == '-' || c == '/' || c == '*';
    }

    public boolean isOp(String s) {
        return isOp(s.charAt(0));
    }

    public BinaryExpression makeOpExpression(String tokenOp, Expression left, Expression right) {
        return switch (tokenOp) {
            case "+" -> new AdditionExpression(left, right);
            case "-" -> new SubstractionExpression(left, right);
            case "*" -> new MultiplicationExpression(left, right);
            case "/" -> new DivisionExpression(left, right);
            default -> null;
        };
    }
}