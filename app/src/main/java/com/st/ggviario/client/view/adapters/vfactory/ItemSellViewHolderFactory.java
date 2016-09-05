package com.st.ggviario.client.view.adapters.vfactory;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.view.adapters.vholders.ItemSellViewHolder;

/**
 * Created by Daniel Costa at 9/3/16.
 * Using user computer xdata
 */
public class ItemSellViewHolderFactory implements ViewHolderFactory {

    @NonNull
    @Override
    public BaseRecyclerAdapter.ItemViewHolder factoryViewHolder(View view) {
        return new ItemSellViewHolder(view);
    }

    @NonNull
    @Override
    public View factoryView(ViewGroup viewGroup, LayoutInflater inflater) {
        return inflater.inflate(getViewType(), viewGroup, false);
    }

    @Override
    public int getViewType() {
        return R.layout.item_sell;
    }
}
