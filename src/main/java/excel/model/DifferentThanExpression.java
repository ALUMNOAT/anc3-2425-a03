package excel.model;

public class DifferentThanExpression extends BinaryExpression {
    public DifferentThanExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Number && rightValue instanceof Number) {
            return !leftValue.equals(rightValue);
        } else if (leftValue instanceof Boolean && rightValue instanceof Boolean) {
            return !leftValue.equals(rightValue);
        } else if (leftValue instanceof String && rightValue instanceof String) {
            return !leftValue.equals(rightValue);
        }
        return "#VALEUR"; // Return error if values are not comparable
    }
}

