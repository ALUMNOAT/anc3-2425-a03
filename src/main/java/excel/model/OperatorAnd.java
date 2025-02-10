package excel.model;

public class OperatorAnd extends Operator {
    @Override
    public int getToken() {
        return 1;
    }

    @Override
    public double apply(double a, double b) {
        return (a != 0 && b != 0) ? 1 : 0;
    }
}