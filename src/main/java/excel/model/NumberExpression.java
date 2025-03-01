package excel.model;

public class NumberExpression extends Expression {

    private final double number;

    public NumberExpression(double number) {
        this.number = number;
    }

    @Override
    public double interpret() {
        return number;
    }
}
