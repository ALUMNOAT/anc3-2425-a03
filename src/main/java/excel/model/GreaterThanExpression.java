package excel.model;

public class GreaterThanExpression extends ComparisonOperator {

    public GreaterThanExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Double && rightValue instanceof Double) {
            return (double) leftValue > (double) rightValue;
        }
        // Exemple : A1=2, A2=sdfdsfsdf, A3=A1>A2, A3 renvoie #VALEUR
        return "#VALEUR";
    }
}