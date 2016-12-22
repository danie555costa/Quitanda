package st.domain.quitanda.client.view.adapters.vholders;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.model.builders.ProductBuilder;
import st.domain.quitanda.client.view.adapters.dataset.ProductDataSet;
import st.domain.quitanda.client.view.callbaks.OnStartActivityItemView;
import st.domain.quitanda.client.view.fragments.SellCarStep;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class ProductViewHolder extends BaseRecyclerAdapter.ItemViewHolder
{
    private final CardView cardView;
    private final TextView titleOperation;
    private final ImageView imageOperation;
    private final OnStartActivityItemView onStartActivityItemView;
    private ProductDataSet dataSet;


    public ProductViewHolder(View itemView, OnStartActivityItemView onStartActivityItemView)
    {
        super(itemView);
        this.cardView = (CardView) itemView;
        this.imageOperation = (ImageView) this.cardView.findViewById(R.id.img_product);
        this.titleOperation = (TextView) this.cardView.findViewById(R.id.tv_product_name);
        this.onStartActivityItemView = onStartActivityItemView;
    }

    public boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position) {
        this.dataSet = (ProductDataSet) dataSet;
        int cardColor =  getContext().getResources().getColor(this.dataSet.getIdColor());
        this.imageOperation.setImageResource(R.drawable.ic_shopping_cart_white_48dp);
        this.titleOperation.setText(this.dataSet);
        this.cardView.setCardBackgroundColor(cardColor);
        return true;
    }

    @Override
    public boolean isClickable(int position) {
        return true;
    }

    @Override
    public void onClink(int position){
        Bundle bundle = new Bundle();
        bundle.putString(SellCarStep.PRODUCT, new ProductBuilder().toXml(this.dataSet.getProduct()));
        Log.i("DBA:APP.TEST", "startActivity is called");
        this.onStartActivityItemView.startActivity(bundle, this.dataSet);

    }
}
