package com.st.ggviario.client.view.events;

import android.app.Activity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class CloseActivityEvent implements MenuObserver
{
    private final OnFinishing onFinishing;
    private List<OnFinishing> onFinishingList;

    public CloseActivityEvent(OnFinishing onFinishing)
    {
        this.onFinishingList = new ArrayList<>();
        this.onFinishing = onFinishing;
    }

    public void add(OnFinishing onFinishing) {
        this.onFinishingList.add(onFinishing);
    }

    @Override
    public boolean accept(MenuItem menuItem, Activity activity)
    {
        int id = menuItem.getItemId();
        for(OnFinishing onFinishing: this.onFinishingList)
                onFinishing.onFinish();
            this.onFinishing.onFinish();
            return true;
    }

    @Override
    public int getKey() {
        return android.R.id.home;
    }

    public interface OnFinishing {
        void onFinish();
    }
}
