package excel.model;

public class LessThanExpression extends Operator implements Expression {

    LessThanExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(Context context) {
        if(left.interpret(context) < right.interpret(context)){
            return 1;
        } else {
            return 0;
        }
    }
}