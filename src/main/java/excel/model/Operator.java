package excel.model;

public abstract class Operator {
    public abstract int getToken(); // Method to get the token value
    public abstract double apply(double a, double b); // Method to apply the operator
}