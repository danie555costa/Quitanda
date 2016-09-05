package com.st.ggviario.client.view.callbaks;

import android.os.Bundle;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public interface OnStartActivityItemView {
    void startActivity(Bundle bundle, BaseRecyclerAdapter.ItemDataSet dataSet);
}
