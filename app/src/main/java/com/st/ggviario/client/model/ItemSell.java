package com.st.ggviario.client.model;

import com.st.dbutil.android.process.ProcessResult;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class ItemSell extends ProcessResult
{
    private final Product product;
    private final Measure measure;
    private final double requestQuantity;

    private final double amountPay;
    private final Double baseQuantity;
    private final double usedQuantity;

    public ItemSell(double amountPay, Double baseQuantity, double usedQuantity, Product product, Measure measure, double requestQuantity) {
        super(amountPay);
        this.amountPay = amountPay;
        this.baseQuantity = baseQuantity;
        this.usedQuantity = usedQuantity;
        this.product = product;
        this.measure = measure;
        this.requestQuantity = requestQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public Measure getMeasure() {
        return measure;
    }

    public double getRequestQuantity() {
        return requestQuantity;
    }

    public double getAmountPay() {
        return amountPay;
    }

    public Double getBaseQuantity() {
        return baseQuantity;
    }

    public double getUsedQuantity() {
        return usedQuantity;
    }
}
