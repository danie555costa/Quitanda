package com.st.ggviario.client.view.adapters.dataset;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Car;
import com.st.ggviario.client.model.ItemSell;
import com.st.ggviario.client.model.rules.CarAction;
import com.st.ggviario.client.util.FormatterFactory;
import com.st.ggviario.client.util.animator.Selectable;

import java.text.NumberFormat;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class CarDataSet implements BaseRecyclerAdapter.ItemDataSet
{
    private final NumberFormat formatter;
    private Car car;
    private CarAction lastAction;
    private ItemSell lastItemSell;

    public CarDataSet(Car car) {
        FormatterFactory formatterFactory = new FormatterFactory();
        this.formatter = formatterFactory.instanceFormatterMoney();
        this.car = car;
    }

    public void setLastAction(CarAction lastAction, ItemSell lastItemSell) {
        this.lastAction = lastAction;
        this.lastItemSell = lastItemSell;
    }

    public CarAction getLastAction() {
        return lastAction;
    }

    public ItemSell getLastItemSell() {
        return lastItemSell;
    }

    @Override
    public int getTypeView() {
        return R.layout.item_group_car;
    }


    public Car getCar() {return this.car;}

    public String getAmount() {
        return formatter.format(this.car.getAmountFinal());
    }
}
