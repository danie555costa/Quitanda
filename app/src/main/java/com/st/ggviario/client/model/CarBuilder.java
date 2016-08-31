package com.st.ggviario.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class CarBuilder implements Builder<Car>
{
    public CarBuilder()
    {
        this.list = new ArrayList<>();
    }

    private double amountFinal;
    private List<ItemSell> list;

    public Car build() {
        return new Car(this.list, this.amountFinal);
    }
}
