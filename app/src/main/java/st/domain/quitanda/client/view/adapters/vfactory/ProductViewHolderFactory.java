package st.domain.quitanda.client.view.adapters.vfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.view.adapters.vholders.ProductViewHolder;
import st.domain.quitanda.client.view.callbaks.OnStartActivityItemView;

/**
 * Created by Daniel Costa at 8/31/16.
 * Using user computer xdata
 */
public class ProductViewHolderFactory implements ViewHolderFactory
{
    private OnStartActivityItemView onStartActivityItemView;

    @Override
    public View factoryView(ViewGroup viewGroup, LayoutInflater inflater) {
        return inflater.inflate(getViewType(), viewGroup, false);
    }

    @Override
    public BaseRecyclerAdapter.ItemViewHolder factoryViewHolder(View view)
    {
        ProductViewHolder viewHolder = new ProductViewHolder(view, this.onStartActivityItemView);
        return viewHolder;
    }

    public ProductViewHolderFactory onStartActivityItemView(OnStartActivityItemView onStartActivityItemView) {
        this.onStartActivityItemView = onStartActivityItemView;
        return this;
    }


    @Override
    public int getViewType() {
        return R.layout.item_product;
    }
}
