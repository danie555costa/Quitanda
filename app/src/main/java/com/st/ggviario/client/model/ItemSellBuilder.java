package com.st.ggviario.client.model;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class ItemSellBuilder implements Builder<ItemSell>
{
    Product product;
    Measure measure;
    int quantity;

    public ItemSellBuilder product(Product product) {
        this.product = product;
        return this;
    }

    public ItemSellBuilder measure(Measure measure) {
        this.measure = measure;
        return this;
    }

    public ItemSellBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public ItemSell build() {
        return new ItemSell(product, quantity, measure);
    }
}
