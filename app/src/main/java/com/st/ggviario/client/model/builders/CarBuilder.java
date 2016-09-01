package com.st.ggviario.client.model.builders;

import com.st.ggviario.client.model.Car;
import com.st.ggviario.client.model.ItemSell;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class CarBuilder extends Builder<Car>
{
    private double amountFinal;
    private List<ItemSell> list;

    public CarBuilder()
    {
        super(Car.class);
        this.list = new ArrayList<>();
    }



    public Car build() {
        Car car = new Car(this.list, this.amountFinal);
        this.list = new ArrayList<>();
        return car;
    }

    /**
     * Create new empty car
     * @return new instance of car
     */
    public Car buildEmpty() {
        this.list.clear();
        Car car = new Car(list, 0);
        this.list = new ArrayList<>();
        return car;
    }
}
