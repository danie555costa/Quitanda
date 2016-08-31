package com.st.ggviario.client.model;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class Conversion
{
    private Measure measure1;
    private  double value1;
    private Measure measure2;
    private double value2;

    public Conversion(Measure measure1, double value1, Measure measure2, double value2) {
        this.measure1 = measure1;
        this.value1 = value1;
        this.measure2 = measure2;
        this.value2 = value2;
    }

    public Measure getMeasure1() {
        return measure1;
    }

    public double getValue1() {
        return value1;
    }

    public Measure getMeasure2() {
        return measure2;
    }

    public double getValue2() {
        return value2;
    }
}
