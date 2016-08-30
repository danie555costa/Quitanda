package com.st.ggviario.client.model;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class ItemSell
{
    Product product;
    int quatity;
    Measure measure;

    ItemSell(Product product, int quantity, Measure measure) {
        this.product = product;
        this.quatity = quantity;
        this.measure = measure;
    }
}
