package excel.model;

public class OrExpression extends Operator implements Expression {

    OrExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(Context context) {

//        if (left.interpret(context) != 0 || right.interpret(context) != 0) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }

        int leftValue = left.interpret(context);
        int rightValue = right.interpret(context);

        // Debug lines to trace the values and computation
        System.out.println("Interpreting OR Expression:");
        System.out.println("Left bool Value: " + leftValue);
        System.out.println("Right bool Value: " + rightValue);

        int result = (leftValue != 0 || rightValue != 0) ? 1 : 0;

        System.out.println("Result of OR Expression: " + result);
        return result;
    }
}