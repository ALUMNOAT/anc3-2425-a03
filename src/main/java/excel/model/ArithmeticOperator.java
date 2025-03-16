package excel.model;

public abstract class ArithmeticOperator extends BinaryExpression {

    public ArithmeticOperator(Expression left, Expression right) {
        super(left, right);
    }

    public Expression getLeft() {
        return super.getLeft();
    }

    public Expression getRight() {
        return super.getRight();
    }
}
