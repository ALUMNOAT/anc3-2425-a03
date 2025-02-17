package excel.model;

public class AndExpression extends Operator implements Expression {

    AndExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(Context context) {
        return (left.interpret(context) != 0 && right.interpret(context) != 0) ? 1 : 0;
    }
}
