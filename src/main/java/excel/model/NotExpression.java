package excel.model;

public class NotExpression extends Operator implements Expression {

    NotExpression(Expression expression) {
        super(expression, null); // On ne nécessite qu'une seule expression pour NOT
    }

    @Override
    public int interpret(Context context) {
        return left.interpret(context) == 0 ? 1 : 0; // TRUE si l'expression est FALSE
    }
}
