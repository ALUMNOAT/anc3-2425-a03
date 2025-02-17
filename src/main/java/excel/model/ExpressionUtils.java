package excel.model;

public class ExpressionUtils {

    // Function to check if an expression is a boolean comparison
    public static boolean isBooleanExpression(String expression) {
        return expression.contains(">") || expression.contains("<") ||
                expression.contains(">=") || expression.contains("<=") ||
                expression.contains("==") || expression.contains("!=");
    }

    // Function to format the result correctly
    public static String formatResult(String expression, int result) {
        if (isBooleanExpression(expression)) {
            return result == 1 ? "true" : "false";
        }
        return String.valueOf(result);
    }
}