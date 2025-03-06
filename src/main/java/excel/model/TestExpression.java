package excel.model;

public class TestExpression {
    public static void main(String[] args) {
        SpreadsheetModel spreadsheet = new SpreadsheetModel(10,10);
        ExpressionBuilder builder = new ExpressionBuilder(spreadsheet);

        String testFormula = "=5+10";
        Expression expression = builder.build(testFormula);

        if (expression != null) {
            System.out.println("Expression built successfully!");
        } else {
            System.out.println("Failed to build expression.");
        }
    }
}
