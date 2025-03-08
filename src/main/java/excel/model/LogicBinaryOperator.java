package excel.model;

public abstract class LogicBinaryOperator extends BinaryExpression {

    public LogicBinaryOperator(Expression left, Expression right) {
        super(left, right);
    }

    public Expression getLeft() {
        return super.getLeft();
    }

    public Expression getRight() {
        return super.getRight();
    }
}
