package st.domain.quitanda.client.model.builders;

import st.domain.quitanda.client.model.ItemSell;
import st.domain.quitanda.client.model.Measure;
import st.domain.quitanda.client.model.Product;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class ItemSellBuilder extends Builder<ItemSell>
{
    private Product product;
    private Measure measure;
    private double requestQuantity;

    private double amountPay;
    private Double baseQuantity;
    private double usedQuantity;


    public ItemSellBuilder() {
        super(ItemSell.class);
    }

    public ItemSellBuilder product(Product product) {
        this.product = product;
        return this;
    }

    public ItemSellBuilder measure(Measure measure) {
        this.measure = measure;
        return this;
    }

    public ItemSellBuilder requestQuantity(double requestQuantity) {
        this.requestQuantity = requestQuantity;
        return this;
    }

    public ItemSellBuilder amountPay(double amountPay) {
        this.amountPay = amountPay;
        return this;
    }

    public ItemSellBuilder baseQuantity(Double baseQuantity) {
        this.baseQuantity = baseQuantity;
        return this;
    }

    public ItemSellBuilder usedQuantity(double usedQuantity) {
        this.usedQuantity = usedQuantity;
        return this;
    }

    @Override
    public ItemSell build() {
        return new ItemSell(amountPay, baseQuantity, usedQuantity, product, measure, requestQuantity);
    }
}
