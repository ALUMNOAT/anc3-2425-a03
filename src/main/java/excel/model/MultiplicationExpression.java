package excel.model;

public class MultiplicationExpression extends ArithmeticOperator {

    public MultiplicationExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Double && rightValue instanceof Double) {
            double leftNum = (double) leftValue;
            double rightNum = (double) rightValue;

            // Optimizations
            if (leftNum == 0 || rightNum == 0) return 0.0; // Multiplication by 0
            if (leftNum == 1) return rightNum; // *1 is unnecessary
            if (rightNum == 1) return leftNum; // *1 is unnecessary

            return leftNum * rightNum;
        }
        // Exemple : A1=2, A2=sdfdsfsdf, A3=A1*A2, A3 renvoie #VALEUR
        if (getLeft() instanceof ReferenceExpression || getRight() instanceof ReferenceExpression) {
            return "#VALEUR";
        }
        return "SYNTAX_ERROR";
    }
}

