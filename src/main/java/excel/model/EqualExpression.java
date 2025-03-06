package excel.model;

public class EqualExpression extends BinaryExpression {
    public EqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        return leftValue.equals(rightValue); // Vérifie l'égalité
    }
}