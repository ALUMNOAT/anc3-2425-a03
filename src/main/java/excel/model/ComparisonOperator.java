package excel.model;

public abstract class ComparisonOperator extends BinaryExpression {

    public ComparisonOperator(Expression left, Expression right) {
        super(left, right);
    }

    public Expression getLeft() {
        return super.getLeft();
    }

    public Expression getRight() {
        return super.getRight();
    }

}
