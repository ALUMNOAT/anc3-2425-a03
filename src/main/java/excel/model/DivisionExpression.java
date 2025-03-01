package excel.model;

public class DivisionExpression extends BinaryExpression {

    public DivisionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double interpret() {
        double denominator = getRight().interpret();
        if (denominator == 0) {
            throw new ArithmeticException("Erreur : Division par zéro.");
        }
        return getLeft().interpret() / denominator;
    }
}
