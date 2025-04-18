package excel.model;

public class AdditionExpression extends ArithmeticOperator {

    public AdditionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        Object rightValue = getRight().interpret();

        if (leftValue instanceof Double && rightValue instanceof Double) {
            return (double) leftValue + (double) rightValue;
        }

        // Exemple : si A1=2, A2=null, et A3=A1+A2, alors A3 renvoie l'erreur en dessous (#VALEUR)
        // (A2 est une référence dans ce cas, et A1 est un Double (pour faire facile))
        if (getLeft() instanceof ReferenceExpression || getRight() instanceof ReferenceExpression) {
            return "#VALEUR";
        }
        return "SYNTAX_ERROR";
    }
}
