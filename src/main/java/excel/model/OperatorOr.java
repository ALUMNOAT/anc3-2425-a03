package excel.model;

public class OperatorOr extends Operator {
    @Override
    public int getToken() {
        return 2; // Token for /
    }

    @Override
    public double apply(double a, double b) {
        return (a != 0 || b != 0) ? 1 : 0;
    }
}