package excel.model;

public abstract class UnaryExpression extends Expression {
    protected final Expression left;

    public UnaryExpression(Expression left) {
        this.left = left;
    }

    public Expression getLeft() {
        return left;
    }
}

