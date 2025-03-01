package excel.model;

public class TestExpression {
    public static void main(String[] args) {
        String s = "= 10 + 50 - 5 * 2 + 90 / 3";
        ExpressionBuilder builder = new ExpressionBuilder();
        Expression exp = builder.build(s);
        System.out.println(exp.interpret());
    }
}

