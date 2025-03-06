package excel.model;

public class OrExpression extends BinaryExpression {
    public OrExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Boolean && rightValue instanceof Boolean) {
            return (Boolean) leftValue || (Boolean) rightValue;
        }
        return "#Valeur"; // Return error if values are not boolean
    }
}
