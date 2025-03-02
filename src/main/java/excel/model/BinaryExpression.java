package excel.model;

public abstract class BinaryExpression implements Expression {
    Expression left;
    Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public abstract int interpret(String context);
}
