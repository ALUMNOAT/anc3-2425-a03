package excel.model;

public class LowerThanExpression extends ComparisonOperator {

    public LowerThanExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Double && rightValue instanceof Double) {
            return (double) leftValue < (double) rightValue;
        }
        return "#VALEUR"; // Invalid comparison
    }
}