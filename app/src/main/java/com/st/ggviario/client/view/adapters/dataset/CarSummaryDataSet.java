package com.st.ggviario.client.view.adapters.dataset;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class CarSummaryDataSet implements BaseRecyclerAdapter.ItemDataSet
{
    @Override
    public int getTypeView() {
        return R.layout.item_group_car;
    }
}
