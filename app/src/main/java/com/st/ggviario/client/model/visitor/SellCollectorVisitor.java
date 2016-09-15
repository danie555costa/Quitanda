package com.st.ggviario.client.model.visitor;

import com.st.ggviario.client.model.Car;
import com.st.ggviario.client.model.Client;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel Costa at 9/6/16.
 * Using user computer xdata
 */
public class SellCollectorVisitor {

    Map<String, Object> collectedMap;
    private Car car;
    private Client client;


    public SellCollectorVisitor() {
        this.collectedMap = new HashMap<>();
    }

    public void collectCar(Car car) {
        this.car = car;
    }

    public void collectClient(Client client) {
        this.client = client;
    }

    public void collectPaymentMode() {
    }

    @Override
    public String toString() {
        return "SellCollectorVisitor{" +
                "collectedMap=" + collectedMap +
                ", car=" + car +
                ", client=" + client +
                '}';
    }
}
