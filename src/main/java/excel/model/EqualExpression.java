package excel.model;

public class EqualExpression extends Operator implements Expression {
    public EqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(Context context) {
        int leftValue = left.interpret(context);
        int rightValue = right.interpret(context);
        System.out.println("Comparing: " + leftValue + " == " + rightValue);
        return leftValue == rightValue ? 1 : 0;
    }
}
