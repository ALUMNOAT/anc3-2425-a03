package excel.model;

public class AndExpression extends Operator implements Expression {

    AndExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpret(Context context) {

//        return (left.interpret(context) != 0 && right.interpret(context) != 0) ? 1 : 0;
//    }
//}

        int leftValue = left.interpret(context);
        int rightValue = right.interpret(context);

        // Debug lines to trace the values and computation
        System.out.println("Interpreting AND Expression:");
        System.out.println("Left bool Value : " + leftValue);
        System.out.println("Right bool Value: " + rightValue);

        int result = (leftValue != 0 && rightValue != 0) ? 1 : 0;

        System.out.println("Result of AND Expression: " + result);
        return result;
    }
}
