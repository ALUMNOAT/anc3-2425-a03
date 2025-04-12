package excel.model;

public class BooleanExpression extends Expression {
    private final boolean value;

    public BooleanExpression(boolean value) {
        this.value = value;
    }

    @Override
    public Object interpret() {
        return value;
    }
}
