package st.domain.quitanda.client.view.adapters.dataset;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.model.Car;
import st.domain.quitanda.client.model.ItemSell;
import st.domain.quitanda.client.model.action.CarAction;
import st.domain.quitanda.client.util.FormatterFactory;

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
