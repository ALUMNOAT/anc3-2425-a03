package excel.model;

public class AndExpression extends BinaryExpression {

    public AndExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Boolean && rightValue instanceof Boolean) {
            return (Boolean) leftValue && (Boolean) rightValue; // Retourne le résultat de AND
        }
        return "#VALEUR"; // Erreur si les valeurs ne sont pas des booléens
    }
}
