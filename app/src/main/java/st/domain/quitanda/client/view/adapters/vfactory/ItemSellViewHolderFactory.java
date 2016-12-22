package st.domain.quitanda.client.view.adapters.vfactory;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.view.adapters.vholders.ItemSellViewHolder;

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
