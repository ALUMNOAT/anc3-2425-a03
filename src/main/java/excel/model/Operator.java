package excel.model;

public abstract class Operator implements Expression {
    Expression left;
    Expression right;

    public Operator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public abstract int interpret(String context);
}
