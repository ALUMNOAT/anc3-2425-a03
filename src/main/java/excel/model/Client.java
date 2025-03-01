package excel.model;

public class Client {
    public static void main(String[] args) {
        // Input expression
        String expression = "= 2 + 10 * 4";

        // Create interpreter
        Builder builder = new Builder(expression);

        // Interpret the expression
        String result = builder.interpret(expression);
        System.out.println("Result: " + result);
    }
}
