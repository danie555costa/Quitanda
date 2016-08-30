package com.st.ggviario.client.model;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class MeasureBuilder implements Builder<Measure>
{
    private double defaultPrice;
    private String id;
    private String name;
    private String key;

    public MeasureBuilder defaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
        return this;
    }

    public MeasureBuilder id(String id) {
        this.id = id;
        return this;
    }

    public MeasureBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MeasureBuilder key(String key) {
        this.key = key;
        return this;
    }

    @Override
    public Measure build() {
        return new Measure(id, key, name, defaultPrice);
    }
}
