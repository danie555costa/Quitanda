package com.st.ggviario.client.model;

import android.widget.TextView;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.model.rules.CarAction;
import com.st.ggviario.client.util.FormatterFactory;
import com.st.ggviario.client.util.animator.Selectable;
import com.st.ggviario.client.view.adapters.dataset.ItemSellDataSet;

import java.text.NumberFormat;

/**
 * Created by Daniel Costa at 9/2/16.
 * Using user computer xdata
 */
public class CarActionAdd extends CarAction
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
        this.car.add(this.item);
        this.adapter.addDataSet(new ItemSellDataSet(this.item));
        this.tvAmount.setText(format.format(this.car.getAmountFinal()));
        this.adapter.notifyItemInserted(this.adapter.getLastIndex());
    }

}
