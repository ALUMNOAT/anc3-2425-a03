package excel.model;

public class MultiplicationExpression extends Operator implements Expression {

        MultiplicationExpression(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public int interpret(Context context) {
            return left.interpret(context) * right.interpret(context);
        }
}
