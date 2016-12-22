package st.domain.quitanda.client.view.events;

import android.app.Activity;
import android.view.MenuItem;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public interface MenuObserver
{
    boolean accept(MenuItem menuItem, Activity activity);

    int getKey();
}
