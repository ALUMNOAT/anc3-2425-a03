package excel.model;

public abstract class LogicUnaryOperator extends UnaryExpression {

    public LogicUnaryOperator(Expression left) {
        super(left);
    }

    public Expression getLeft() {
        return super.getLeft();
    }
}
