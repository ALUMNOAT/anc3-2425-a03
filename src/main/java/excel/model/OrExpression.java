package excel.model;

public class OrExpression extends Operator implements Expression {

    OrExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(Context context) {
        if(left.interpret(context) != 0 || right.interpret(context) != 0){
            return 1;
        } else {
            return 0;
        }
    }
}