package com.st.ggviario.client.model;

import com.st.dbutil.android.process.ProcessResult;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class ItemSell extends ProcessResult
{
    private Product product;
    private Measure measure;
    private double requestQuantity;

    private double amountPay;
    private Double baseQuantity;
    private double usedQuantity;

    public ItemSell() {
        super(0);
    }

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

    public double getUsedQuantity() {
        return usedQuantity;
    }

}
