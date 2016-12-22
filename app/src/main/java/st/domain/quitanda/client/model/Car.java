package st.domain.quitanda.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class Car
{
    private List<ItemSell> itemSellList;
    private double amountFinal;


    public Car()
    {
        this.itemSellList = new ArrayList<>();
        this.amountFinal = 0;
    }


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
        int index = this.getIndex(product);
        if(index != -1)
        {
            ItemSell item = itemSellList.remove(index);
            this.amountFinal -= item.getAmountPay();
            return item;
        }
        return null;
    }


    public boolean contains(Product product)
    {
        for(ItemSell sell: this.itemSellList)
            if(sell.getProduct().equals(product)) return true;
        return false;
    }


    public boolean isEmpty() {
        return this.itemSellList.isEmpty();
    }


    public List<ItemSell> getItemSellList() {
        return Collections.unmodifiableList(this.itemSellList);
    }


    /**
     * Renovar o carinho
     * @param itemSell
     */
    public void replace(ItemSell itemSell) {
        int index = this.getIndex(itemSell.getProduct());
        this.remove(itemSell.getProduct());
        this.itemSellList.add(index, itemSell);
        this.amountFinal += itemSell.getAmountPay();
    }

    public int getIndex(Product product) {
        int count = 0;
        for(ItemSell itemSellList: this.itemSellList) {
            if (itemSellList.getProduct().equals(product))
                return count;
            count++;
        }
        return -1;
    }

    public int countItems() {
        return this.itemSellList.size();
    }

    public ItemSell getItem(Product product) {
        int index = this.getIndex(product);
        return index!= -1? itemSellList.get(index): null;
    }

    public double getAmountFinal() {
        return amountFinal;
    }
}
