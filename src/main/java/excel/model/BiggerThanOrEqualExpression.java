package excel.model;

public class BiggerThanOrEqualExpression extends ComparisonOperator {

    public BiggerThanOrEqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Double && rightValue instanceof Double) {
            return (double) leftValue >= (double) rightValue;
        }
        // Exemple : Si A1=7, A2=null, et A3=A1>=A2, alors A3 renvoie #VALEUR
        return "#VALEUR";
    }
}

