package excel.model;

public class NotExpression extends LogicUnaryOperator {
    public NotExpression(Expression left) {
        super(left);
    }

    @Override
    public Object interpret() {
        Object leftValue = getLeft().interpret();
        if (leftValue instanceof Boolean) {
            return !(Boolean) leftValue;
        }
        return "#Valeur"; // Return error if the value is not boolean
    }
}


