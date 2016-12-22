package st.domain.quitanda.client.util.animator;

import st.domain.support.android.adapter.BaseRecyclerAdapter;

/**
 * Created by Daniel Costa at 9/4/16.
 * Using user computer xdata
 */
public interface OnAnimateSelection {
    void onPreAnimate(BaseRecyclerAdapter.ItemViewHolder itemViewHolder, Selectable selectable);

    void onPosAnimate(BaseRecyclerAdapter.ItemViewHolder itemViewHolder, Selectable selectable);
}
