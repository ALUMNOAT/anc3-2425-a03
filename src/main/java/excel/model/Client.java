package excel.model;

public class Client {
    public static void main(String[] args) {
        // Input expression
        String expression = "= 2 + 10 * 4";

        // Create interpreter
        Context context = new Context(expression);
        Builder builder = new Builder(context);

        // Interpret the expression
        int result = builder.interpret(expression);
        System.out.println("Result: " + result);
    }
}
