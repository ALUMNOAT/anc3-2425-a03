package excel.model;

public class SubstractionExpression extends ArithmeticOperator {

    public SubstractionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Double && rightValue instanceof Double) {
            double leftNum = (double) leftValue;
            double rightNum = (double) rightValue;

            // Optimization: x - 0 = x
            if (rightNum == 0) return leftNum;

            return leftNum - rightNum;
        }
        return "SYNTAX_ERROR";
    }
}
