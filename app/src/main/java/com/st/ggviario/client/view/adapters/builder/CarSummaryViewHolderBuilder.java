package com.st.ggviario.client.view.adapters.builder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.view.adapters.vholders.CarSummaryViewHolder;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class CarSummaryViewHolderBuilder implements ViewHolderBuilder
{

    @Override
    public BaseRecyclerAdapter.ItemViewHolder createViewHolder(View view)
    {
        CarSummaryViewHolder viewHolder = new CarSummaryViewHolder(view);
        return  viewHolder;
    }

    @Override
    public View createView(ViewGroup viewGroup, LayoutInflater inflater) {
        return inflater.inflate(getViewType(), viewGroup, false);
    }

    @Override
    public int getViewType() {
        return R.layout.item_group_car;
    }
}
