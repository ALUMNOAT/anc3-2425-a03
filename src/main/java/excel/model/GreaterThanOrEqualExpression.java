package excel.model;

public class GreaterThanOrEqualExpression extends Operator implements Expression {

    GreaterThanOrEqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(Context context) {
        if(left.interpret(context) >= right.interpret(context)){
            return 1;
        } else {
            return 0;
        }
    }
}