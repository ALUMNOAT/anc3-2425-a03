package excel.model;

public class SubstractionExpression extends BinaryExpression {

    SubstractionExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(String context) {
        return left.interpret(context) - right.interpret(context);
    }

}
