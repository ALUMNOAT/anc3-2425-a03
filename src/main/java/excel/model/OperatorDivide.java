package excel.model;

public class OperatorDivide extends Operator {
    @Override
    public int getToken() {
        return 2; // Token for /
    }

    @Override
    public double apply(double a, double b) {
        if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
        return a / b;
    }
}