package fraktalis.androidtestapp.model;

public class Counter {

    private double $value = 0f;

    public Counter addValue(double $v) {
        $value += $v;

        return this;
    }

    public double getValue() {
        return $value;
    }
}
