package com.st.ggviario.client.model;

import java.util.List;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class Car
{
    private List<ItemSell> itemSellList;
    private double amountFinal;

    public Car(List<ItemSell> itemSellList, double amountFinal)
    {
        this.itemSellList = itemSellList;
        this.amountFinal = amountFinal;
    }

    /**
     * Add the iten to car
     * @param itemSell
     */
    public void add(ItemSell itemSell)
    {
        this.itemSellList.add(itemSell);
        this.amountFinal += itemSell.getAmountPay();
    }

    /**
     * Remove the item in car
     * @param product
     * @return
     */
    public ItemSell remove(Product product)
    {
        for(int i =0; i< this.itemSellList.size(); i++)
        {
            if(itemSellList.get(i).getProduct().getId() == product.getId())
            {
                ItemSell item = itemSellList.remove(i);
                this.amountFinal -= item.getAmountPay();
                return item;
            }
        }
        return null;
    }
}
