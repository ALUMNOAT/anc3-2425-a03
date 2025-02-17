package excel.model;

public class SubstractionExpression extends Operator implements Expression {

    SubstractionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(Context context) {
        return left.interpret(context) - right.interpret(context);
    }
}
