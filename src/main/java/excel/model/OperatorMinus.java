package excel.model;

public class OperatorMinus extends Operator {
    @Override
    public int getToken() {
        return 1; // Token for -
    }

    @Override
    public double apply(double a, double b) {
        return a - b;
    }
}