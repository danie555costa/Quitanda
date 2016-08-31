package com.st.ggviario.client.view.events;

import android.app.Activity;
import android.view.MenuItem;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public interface EventAction
{
    boolean accept(MenuItem menuItem, Activity appCompatActivity);
}
