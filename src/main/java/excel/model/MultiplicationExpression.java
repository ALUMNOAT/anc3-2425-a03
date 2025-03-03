package excel.model;

public class MultiplicationExpression extends BinaryExpression {

    public MultiplicationExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double interpret() {
        double leftVal = getLeft().interpret();
        double rightVal = getRight().interpret();

        if (leftVal == 0 || rightVal == 0) return 0; // Optimisation : multiplication par 0
        if (leftVal == 1) return rightVal; // Optimisation : *1 inutile
        if (rightVal == 1) return leftVal; // Optimisation : *1 inutile

        return leftVal * rightVal;
    }
}
