package com.st.ggviario.client.model.action;

import android.widget.TextView;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.model.Car;
import com.st.ggviario.client.model.ItemSell;
import com.st.ggviario.client.view.adapters.dataset.ItemSellDataSet;

import java.io.Serializable;

/**
 * Created by Daniel Costa at 9/2/16.
 * Using user computer xdata
 */
public abstract class CarAction implements Serializable
{
    public abstract CarAction item(ItemSell itemSell);

    public abstract CarAction car(Car car);

    public abstract CarAction amountView(TextView tvAmountPay);

    public abstract CarAction adapter(BaseRecyclerAdapter adapter);

    public abstract void execute();

    /**
     * Popular o item sell
     * @param adapter
     * @param car
     */
    protected void populateAdapter(BaseRecyclerAdapter adapter, Car car)
    {
        adapter.clear();
        for(ItemSell itemSell: car.getItemSellList())
            adapter.addDataSet(new ItemSellDataSet(itemSell));
    }

}
