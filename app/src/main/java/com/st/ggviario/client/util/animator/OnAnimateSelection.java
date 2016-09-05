package com.st.ggviario.client.util.animator;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;

/**
 * Created by Daniel Costa at 9/4/16.
 * Using user computer xdata
 */
public interface OnAnimateSelection {
    void onPreAnimate(BaseRecyclerAdapter.ItemViewHolder itemViewHolder, Selectable selectable);

    void onPosAnimate(BaseRecyclerAdapter.ItemViewHolder itemViewHolder, Selectable selectable);
}
