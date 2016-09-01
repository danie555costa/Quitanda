package com.st.ggviario.client.view.events;

import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;

import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Car;
import com.st.ggviario.client.model.ItemSell;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.model.contract.ObserverCalculated;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class CarEvemtAction implements MenuObserver, ObserverCalculated {
    private final Product product;
    private Car car;
    private ItemSell itemSell;

    public CarEvemtAction(Car car, Product product)
    {
        this.car = car;
        this.product = product;
    }

    @Override
    public boolean accept(MenuItem menuItem, Activity appCompatActivity)
    {
        if(menuItem.getItemId() == R.id.opt_car_action)
        {
            boolean contains = this.car.contains(this.product);
            if(contains)
            {
                Log.i("DBA:APP.TEST", "Removing one item in car");
                this.car.remove(product);
                menuItem.setIcon(R.drawable.ic_add_shopping_cart_white_24dp);
                menuItem.setTitle(R.string.add);
            }
            else if(this.itemSell != null)
            {
                Log.i("DBA:APP.TEST", "Adding new item in car");
                this.car.add(itemSell);
                menuItem.setIcon(R.drawable.ic_remove_shopping_cart_white_24dp);
                menuItem.setTitle(R.string.remove);
            }
            return true;
        }
        return false;
    }

    @Override
    public void accept(ItemSell itemSell)
    {
        Log.i("DBA:APP.TEST", getClass().getSimpleName()+"-> accept on car event");
        this.itemSell = itemSell;
    }
}
