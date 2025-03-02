package excel.model;

public class MultiplicationExpression extends BinaryExpression {

        MultiplicationExpression(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public int interpret(String context) {
            return left.interpret(context) * right.interpret(context);
        }
        
}
