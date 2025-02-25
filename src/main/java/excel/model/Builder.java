package excel.model;

import java.util.Stack;

public class Builder {
    private String context;

    public Builder(String context) {
        this.context = context;
    }

    public int interpret(String expression) {
        // Parse the expression and create the syntax tree
        Expression expressionTree = buildExpressionTree(expression);
        // Interpret the expression tree
        return expressionTree.interpret(context);
    }

    private Expression buildExpressionTree(String expression) {
        // Utiliser un StringBuilder pour analyser l'expression
        StringBuilder sb = new StringBuilder();
        Stack<Expression> stack = new Stack<>();
        Stack<Character> operators = new Stack<>();

        if (expression.startsWith("=")) {
            expression = expression.substring(1);
            for (char ch : expression.toCharArray()) {
                if (Character.isDigit(ch)) {
                    sb.append(ch);
                } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                    if (sb.length() > 0) {
                        stack.push(new NumberExpression(Integer.parseInt(sb.toString())));
                        sb.setLength(0);
                    }
                    while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                        stack.push(createExpression(operators.pop(), stack.pop(), stack.pop()));
                    }
                    operators.push(ch);
                }
            }
        }

        if (sb.length() > 0) {
            stack.push(new NumberExpression(Integer.parseInt(sb.toString())));
        }

        while (!operators.isEmpty()) {
            stack.push(createExpression(operators.pop(), stack.pop(), stack.pop()));
        }

        return stack.pop();
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private Expression createExpression(char operator, Expression right, Expression left) {
        switch (operator) {
            case '+':
                return new AdditionExpression(left, right);
            case '-':
                return new SubstractionExpression(left, right);
            case '*':
                return new MultiplicationExpression(left, right);
            case '/':
                return new DivisionExpression(left, right);
            default:
                throw new IllegalArgumentException("Opérateur inconnu: " + operator);
        }
    }
}
