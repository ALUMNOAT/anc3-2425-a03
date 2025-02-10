package excel.model;

public class DivisionExpression extends Operator {

    DivisionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(Context context) {
        return left.interpret(context) / right.interpret(context);
    }

}
