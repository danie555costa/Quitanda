package com.st.ggviario.client.view.events;

import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Car;
import com.st.ggviario.client.model.CarActionAdd;
import com.st.ggviario.client.model.CarActionRemove;
import com.st.ggviario.client.model.CarActionReplace;
import com.st.ggviario.client.model.ItemSell;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.model.contract.ObserverCalculated;
import com.st.ggviario.client.model.rules.CarAction;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class CarActionEvent implements MenuObserver, ObserverCalculated
{
    private final Product product;
    private final boolean hasIncar;
    private ItemSell itemSell;
    private boolean add;

    public CarActionEvent(Product product, boolean hasIncar)
    {
        this.product = product;
        this.hasIncar = hasIncar;
        this.add = hasIncar;

        Log.i("DBA:APP.TEST", "event in car add:"+ this.add+" | has_in_car: "+hasIncar);
    }

    public void onMenu(Menu menu) {
        if(this.hasIncar && add)
            menu.findItem(getKey()).setIcon(R.drawable.ic_remove_shopping_cart_white_24dp);
    }

    @Override
    public boolean accept(MenuItem menuItem, Activity activity)
    {
        if(add)
        {
            this.add = false;
            menuItem.setIcon(R.drawable.ic_add_shopping_cart_white_24dp);
            menuItem.setTitle(R.string.add);
        }
        else if(this.itemSell != null)
        {
            this.add = true;
            menuItem.setIcon(R.drawable.ic_remove_shopping_cart_white_24dp);
            menuItem.setTitle(R.string.remove);
        }
        return true;
    }

    public CarAction getCarAction() {
        if(add && hasIncar) return  new CarActionReplace();
        else if(add && !hasIncar) return new CarActionAdd();
        else return new CarActionRemove();
    }

    @Override
    public int getKey() {
        return  R.id.opt_car_action;
    }

    @Override
    public void accept(ItemSell itemSell)
    {
        Log.i("DBA:APP.TEST", getClass().getSimpleName()+"-> accept on car event");
        this.itemSell = itemSell;
    }
}
