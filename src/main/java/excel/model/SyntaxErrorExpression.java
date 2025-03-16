package excel.model;

public class SyntaxErrorExpression extends Expression{
    private final String message;

    public SyntaxErrorExpression(String message) {
        this.message = message;
    }

    @Override
    public Object interpret() {
        return message;
    }

}
