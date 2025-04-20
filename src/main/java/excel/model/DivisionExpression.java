package excel.model;

public class DivisionExpression extends ArithmeticOperator {

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
            // Exemple : A1=2/0, renvoie #VALEUR
            // A1=2, A2=0, A3=A1/A2, A3 renvoie #VALEUR
            if (denominator == 0) {
                return "#VALEUR";
            }
            return (double) leftValue / denominator;
        }

        // Exemple : Si A1=2, A2=null, et A3=A1/A2, A3 renvoie #VALEUR
        if (getLeft() instanceof ReferenceExpression || getRight() instanceof ReferenceExpression) {
            return "#VALEUR";
        }
        return "SYNTAX_ERROR";
    }
}

