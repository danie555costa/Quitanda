package com.st.ggviario.client.view.events;

import android.app.Activity;
import android.view.MenuItem;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class CloseActivityEvent implements MenuObserver
{
    @Override
    public boolean accept(MenuItem menuItem, Activity activity)
    {
        int id = menuItem.getItemId();
        if(id == android.R.id.home)
        {
            activity.finish();
            return true;
        }
        return false;
    }
}
