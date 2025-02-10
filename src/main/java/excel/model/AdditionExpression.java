package excel.model;

public class AdditionExpression extends Operator implements Expression {

    AdditionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(Context context) {
        return left.interpret(context) + right.interpret(context);
    }
}
