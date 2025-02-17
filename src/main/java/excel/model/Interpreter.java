package excel.model;

import java.util.Stack;

public class Interpreter {
    private Context context;

    public Interpreter(Context context) {
        this.context = context;
    }

    public int interpret(String expression) {
        // Parse the expression and create the syntax tree
        Expression expressionTree = buildExpressionTree(expression);
        // Interpret the expression tree
        return expressionTree.interpret(context);
    }

    private Expression buildExpressionTree(String expression) {
        StringBuilder sb = new StringBuilder();
        Stack<Expression> stack = new Stack<>();
        Stack<String> operators = new Stack<>();

        if (expression.startsWith("=")) {
            expression = expression.substring(1);
            int i = 0;
            while (i < expression.length()) {
                char ch = expression.charAt(i);

                //check for 3 characters operators (and, not)
                if (i + 2 < expression.length()) {
                    String threeCharOp = expression.substring(i, i + 3);
                    if(isOperator(threeCharOp)) {
                        processOperator(sb, stack, operators, threeCharOp);
                        i += 3; //Move by 3 characters
                        continue;
                    }
                }

                // Check for 2 characters operators (>=, <=, ==, !=, or)
                if (i + 1 < expression.length()) {
                    String twoCharOp = expression.substring(i, i + 2);
                    if (isOperator(twoCharOp)) {
                        processOperator(sb, stack, operators, twoCharOp);
                        i += 2; // Move by 2 characters
                        continue;
                    }
                }

                // Check for single-character operators (+, -, *, /, >, <)
                if (isOperator(String.valueOf(ch))) {
                    processOperator(sb, stack, operators, String.valueOf(ch));
                } else if (Character.isDigit(ch) || ch == '-') { // Autoriser les chiffres et signe négatif
                    sb.append(ch); // Collect number characters
                }
                i++;
            }
        }

        // Push last number to stack
        if (sb.length() > 0) {
            String numberStr = sb.toString().trim(); // Remove spaces
            stack.push(new NumberExpression(Integer.parseInt(numberStr)));
        }

        // Process remaining operators
        while (!operators.isEmpty()) {
            stack.push(createExpression(operators.pop(), stack.pop(), stack.pop()));
        }

        return stack.pop();
    }

    private void processOperator(StringBuilder sb, Stack<Expression> stack, Stack<String> operators, String operator) {
        if (sb.length() > 0) {
            stack.push(new NumberExpression(Integer.parseInt(sb.toString())));
            sb.setLength(0);
        }
        while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(operator)) {
            stack.push(createExpression(operators.pop(), stack.pop(), stack.pop()));
        }
        operators.push(operator);
    }

    private boolean isOperator(String op) {
        return op.equals(">") || op.equals("<") || op.equals(">=") || op.equals("<=") ||
                op.equals("==") || op.equals("!=") || op.equals("+") || op.equals("-") ||
                op.equals("*") || op.equals("/") || op.equals("not") || op.equals("and") || op.equals("or");
    }

    private int precedence(String operator) {
        return switch (operator) {
            case "*", "/" -> 6;
            case "+", "-" -> 5;
            case ">", "<", ">=", "<=", "==", "!=" -> 4;
            case "not" -> 3;
            case "and" -> 2;
            case "or" -> 1;
            default -> -1;
        };
    }

    private Expression createExpression(String operator, Expression right, Expression left) {
        return switch (operator) {
            case "or" -> new OrExpression(left, right);
            case "and" -> new AndExpression(left, right);
            case "not" -> new NotExpression(left);
            case ">" -> new GreaterThanExpression(left, right);
            case "<" -> new LessThanExpression(left, right);
            case ">=" -> new GreaterThanOrEqualExpression(left, right);
            case "<=" -> new LessThanOrEqualExpression(left, right);
            case "==" -> new EqualExpression(left, right);
            case "!=" -> new NotEqualExpression(left, right);
            case "+" -> new AdditionExpression(left, right);
            case "-" -> new SubstractionExpression(left, right);
            case "*" -> new MultiplicationExpression(left, right);
            case "/" -> new DivisionExpression(left, right);
            default -> throw new IllegalArgumentException("Operateur inconnu: " + operator);
        };
    }
}