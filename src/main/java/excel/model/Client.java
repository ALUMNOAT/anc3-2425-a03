package excel.model;

public class Client {
    public static void main(String[] args) {
        // Input expression
        String expression = "=1>8 and 1<8";

        // Create interpreter
        Context context = new Context(expression);
        Interpreter interpreter = new Interpreter(context);

        // Interpret the expression
        int result = interpreter.interpret(expression);
        System.out.println("Result: " + ExpressionUtils.formatResult(expression, result));
    }
}