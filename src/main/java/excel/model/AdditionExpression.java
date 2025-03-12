package excel.model;

public class AdditionExpression extends ArithmeticOperator {

    public AdditionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Double && rightValue instanceof Double) {
            return (double) leftValue + (double) rightValue;
        }
        if (getLeft() instanceof ReferenceExpression || getRight() instanceof ReferenceExpression) {
            return "#VALEUR";
        }
        return "SYNTAX_ERROR";
    }
}
