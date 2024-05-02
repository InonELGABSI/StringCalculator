package test;

public class Number implements Expression{
    private final double value;

    public Number(double num) {
        this.value = num;
    }

    @Override
    public double calculate() {
        return this.value;
    }
}
