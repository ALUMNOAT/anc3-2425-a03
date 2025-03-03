package excel.model;

public class DivisionExpression extends BinaryExpression {

    public DivisionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object rightValue = getRight().interpret();
        Object leftValue = getLeft().interpret();

        // Vérifier que les deux opérandes sont bien des nombres
        if (rightValue instanceof Double && leftValue instanceof Double) {
            double denominator = (double) rightValue;
            if (denominator == 0) {
                return "#VALEUR";
            }
            return (double) leftValue / denominator;
        }
        return "#VALEUR"; // Si un des opérandes n'est pas un nombre
    }
}

