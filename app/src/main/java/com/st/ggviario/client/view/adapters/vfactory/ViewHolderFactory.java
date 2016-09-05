package com.st.ggviario.client.view.adapters.vfactory;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;

/**
 * Created by Daniel Costa at 8/31/16.
 * Using user computer xdata
 */
public interface ViewHolderFactory {

    @NonNull View factoryView(ViewGroup viewGroup, LayoutInflater inflater);

    @NonNull BaseRecyclerAdapter.ItemViewHolder factoryViewHolder(View view);

    int getViewType();

}
