package com.st.ggviario.client.model.action;

import android.widget.TextView;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.model.Car;
import com.st.ggviario.client.model.ItemSell;
import com.st.ggviario.client.util.FormatterFactory;
import com.st.ggviario.client.view.adapters.dataset.ItemSellDataSet;

import java.text.NumberFormat;

/**
 * Created by Daniel Costa at 9/2/16.
 * Using user computer xdata
 */
public class CarActionReplace extends CarAction
{
    private ItemSell item;
    private Car car;
    private TextView tvAmount;
    private BaseRecyclerAdapter adapter;

    @Override
    public CarAction item(ItemSell itemSell) {
        this.item = itemSell;
        return this;
    }

    @Override
    public CarAction car(Car car) {
        this.car = car;
        return this;
    }

    @Override
    public CarAction amountView(TextView tvAmountPay) {
        this.tvAmount = tvAmountPay;
        return this;
    }

    @Override
    public CarAction adapter(BaseRecyclerAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    @Override
    public void execute() {
        this.populateAdapter(this.adapter, this.car);
        NumberFormat format = new FormatterFactory().instanceFormatterMoney();
        int index = this.car.getIndex(this.item.getProduct());

        this.car.replace(this.item);
        this.adapter.replace(index, new ItemSellDataSet(this.item));
        this.tvAmount.setText(format.format(this.car.getAmountFinal()));
        this.adapter.notifyDataSetChanged();
    }
}