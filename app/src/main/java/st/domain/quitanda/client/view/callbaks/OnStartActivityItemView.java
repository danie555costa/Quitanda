package st.domain.quitanda.client.view.callbaks;

import android.os.Bundle;

import st.domain.support.android.adapter.BaseRecyclerAdapter;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public interface OnStartActivityItemView {
    void startActivity(Bundle bundle, BaseRecyclerAdapter.ItemDataSet dataSet);
}
