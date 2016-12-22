package st.domain.quitanda.client.view.callbaks;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Daniel Costa at 9/6/16.
 * Using user computer xdata
 */
public interface OnActivityResultObserver {
    void onResult(Activity activity, Intent intent);

    void onResultEmpty(Activity activity);
}
