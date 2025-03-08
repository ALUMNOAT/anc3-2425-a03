package excel.model;

public class BiggerThanOrEqualExpression extends ComparisonOperator {

    public BiggerThanOrEqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Double && rightValue instanceof Double) {
            return (double) leftValue >= (double) rightValue;
        }
        return "#VALEUR";
    }
}

