package excel.model;

import org.w3c.dom.ls.LSOutput;

public class Client {
    public static void main(String[] args) {
        // Input expression
        String expression = "= 2 + 10 / 5";

        // Create interpreter
        Context context = new Context(expression);
        Interpreter interpreter = new Interpreter(context);

        // Interpret the expression
        int result = interpreter.interpret(expression);
        System.out.println("Result: " + result);
    }
}
