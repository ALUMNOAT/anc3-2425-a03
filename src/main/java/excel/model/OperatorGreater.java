package excel.model;

public class OperatorGreater extends Operator {
    @Override
    public int getToken() {
        return 1;
    }

    @Override
    public double apply(double a, double b) {
        return (a > b) ? 1 : 0;
    }
}