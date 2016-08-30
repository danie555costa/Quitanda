package com.st.ggviario.client.model;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class ProductBuilder implements Builder<Product>
{
    private String name;
    private String id;

    public ProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public Product build() {
        return new Product(id, name);
    }
}
