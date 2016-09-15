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
    private List<OnFinishing> onFinishingList;
    private int idMenu;

    public CloseActivityEvent()
    {
        this.onFinishingList = new ArrayList<>();
        this.idMenu =  android.R.id.home;
    }

    public void setMenuId(int idMenu) {
        this.idMenu = idMenu;
    }

    public void add(OnFinishing onFinishing) {
        this.onFinishingList.add(onFinishing);
    }

    @Override
    public boolean accept(MenuItem menuItem, Activity activity)
    {
        for(OnFinishing onFinishing: this.onFinishingList)
                onFinishing.onFinish(activity);
        activity.finish();
        return true;
    }

    @Override
    public int getKey() {
        return  this.idMenu;
    }

    public interface OnFinishing {
        void onFinish(Activity activity);
    }
}
