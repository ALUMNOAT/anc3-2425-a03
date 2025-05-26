package excel.model;

public class PowerExpression extends BinaryExpression {
    public PowerExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Double && rightValue instanceof Double) {
            double leftNum = (double) leftValue;
            double rightNum = (double) rightValue;

            return Math.pow(leftNum, rightNum);
        }
        // Exemple : A1=2, A2=sdfdsfsdf, A3=A1*A2, A3 renvoie #VALEUR
        if (getLeft() instanceof ReferenceExpression || getRight() instanceof ReferenceExpression) {
            return "#VALEUR";
        }
        return "SYNTAX_ERROR";
    }
}
