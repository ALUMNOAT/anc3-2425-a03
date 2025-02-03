package excel.model;

import java.util.Stack;

public class Expression {
    private Stack<Double> values = new Stack<>();
    private Stack<Operator> operators = new Stack<>();

    public void pushValue(double value) {
        values.push(value); // Push a value onto the stack
    }

    public void pushOperator(Operator operator) {
        while (!operators.isEmpty() && operator.getToken() <= operators.peek().getToken()) {
            double b = values.pop(); // Second operand
            double a = values.pop(); // First operand
            values.push(operators.pop().apply(a, b)); // Evaluate and push the result
        }
        operators.push(operator); // Push the new operator
    }

    public double eval() {
        while (!operators.isEmpty()) {
            double b = values.pop(); // Second operand
            double a = values.pop(); // First operand
            values.push(operators.pop().apply(a, b)); // Evaluate and push the result
        }
        return values.pop(); // Return the final result
    }
}