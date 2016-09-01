package com.st.ggviario.client.view.adapters.builder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;

/**
 * Created by Daniel Costa at 8/31/16.
 * Using user computer xdata
 */
public interface ViewHolderBuilder {

    BaseRecyclerAdapter.ItemViewHolder createViewHolder(View view);

    View createView(ViewGroup viewGroup, LayoutInflater inflater);

    int getViewType();


}
