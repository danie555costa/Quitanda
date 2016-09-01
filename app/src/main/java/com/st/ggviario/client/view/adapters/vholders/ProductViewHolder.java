package com.st.ggviario.client.view.adapters.vholders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.model.builders.CarBuilder;
import com.st.ggviario.client.model.builders.ProductBuilder;
import com.st.ggviario.client.view.activitys.CalculatorActivity;
import com.st.ggviario.client.view.adapters.dataset.DataProduct;
import com.st.ggviario.client.view.fragments.SellCarStep;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class ProductViewHolder extends BaseRecyclerAdapter.ItemViewHolder
{
    private final CardView cardView;
    private final TextView titleOperation;
    private final ImageView imageOperation;
    private DataProduct dataSet;


    public ProductViewHolder(View itemView)
    {
        super(itemView);
        this.cardView = (CardView) itemView;
        this.imageOperation = (ImageView) this.cardView.findViewById(R.id.img_operation);
        this.titleOperation = (TextView) this.cardView.findViewById(R.id.tv_title_operation);
    }

    public boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position) {
        this.dataSet = (DataProduct) dataSet;
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
        Intent intent = new Intent(this.getContext(), CalculatorActivity.class);
        Bundle bundle = new Bundle();
        CarBuilder carBuilder = new CarBuilder();

        bundle.putString(SellCarStep.PRODUCT, new ProductBuilder().toXml(this.dataSet.getProduct()));
        bundle.putString(SellCarStep.CAR, carBuilder.toXml(carBuilder.build()));
        intent.putExtras(bundle);
        getContext().startActivity(intent);
    }
}
