package com.st.ggviario.client.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.dbutil.android.adapter.SupportRecyclerAdapter;
import com.st.ggviario.client.view.adapters.builder.ViewHolderBuilder;

import java.util.HashMap;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class ViewHolderAdapter extends BaseRecyclerAdapter implements SupportRecyclerAdapter.OnCreateViewHolder {
    private final HashMap<Integer, ViewHolderBuilder> mapViewHolder;

    public ViewHolderAdapter(Context context) {
        super(context, null);
        mapViewHolder = new HashMap<>();
    }


    @Override
    protected ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType, LayoutInflater inflater) {
        View view = this.mapViewHolder.get(viewType).createView(viewGroup, inflater);
        return this.mapViewHolder.get(viewType).createViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ItemViewHolder holder, ItemDataSet data, int position) {

    }

    public void addViewHolderBuilder(ViewHolderBuilder viewHolderBuilder) {
        this.mapViewHolder.put(viewHolderBuilder.getViewType(), viewHolderBuilder);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(View view, int viewType, int onRecyclerViewId) {
        return this.mapViewHolder.get(viewType).createViewHolder(view);
    }
}
