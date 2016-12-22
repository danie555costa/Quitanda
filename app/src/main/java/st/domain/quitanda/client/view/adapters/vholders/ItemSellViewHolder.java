package st.domain.quitanda.client.view.adapters.vholders;

import android.view.View;
import android.widget.TextView;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.view.adapters.dataset.ItemSellDataSet;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class ItemSellViewHolder extends BaseRecyclerAdapter.ItemViewHolder{

    private final TextView tvProduct;
    private final TextView tvQuantity;
    private final TextView tvAmount;
    private ItemSellDataSet value;

    public ItemSellViewHolder(View itemView) {
        super(itemView);
        this.tvProduct = (TextView) itemView.findViewById(R.id.tv_item_sell_product);
        this.tvQuantity = (TextView) itemView.findViewById(R.id.tv_item_sell_quantity);
        this.tvAmount = (TextView) itemView.findViewById(R.id.tv_item_sell_amount);
    }

    @Override
    public boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position) {
        this.value = (ItemSellDataSet) dataSet;
        this.tvProduct.setText(value.getProduct());
        this.tvAmount.setText(value.getAmount());
        this.tvQuantity.setText(value.getQuantity());
        return true;
    }
}
