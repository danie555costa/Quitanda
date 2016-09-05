package com.st.ggviario.client.view.adapters.vholders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.view.adapters.vfactory.ItemSellViewHolderFactory;
import com.st.ggviario.client.view.adapters.dataset.CarDataSet;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class CarViewHolder extends BaseRecyclerAdapter.ItemViewHolder
{
    RecyclerView recyclerView;
    private CarDataSet value;
    private SupportAdapter supportItemSell;
    private TextView tvAmountPay;

    public CarViewHolder(View view)
    {
        super(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_car_products);
        this.tvAmountPay = (TextView) view.findViewById(R.id.tv_car_amount_pay);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        this.supportItemSell = new SupportAdapter(getContext());
        this.supportItemSell.addViewHolderFactory(new ItemSellViewHolderFactory());
        this.recyclerView.setAdapter(this.supportItemSell);
        modifyLayout();
    }

    @Override
    public boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position) {
        Log.i("DBA:APP.TEST", "bind carViewHolder");
        this.value= (CarDataSet) dataSet;
        if(this.value.getLastAction() != null)
        {
            value.getLastAction()
                    .item(this.value.getLastItemSell())
                    .car(this.value.getCar())
                    .amountView(tvAmountPay)
                    .adapter(this.supportItemSell)
                    .execute();
            this.value.setLastAction(null, null);
        }
        else this.tvAmountPay.setText("0,00 STD");
        return true;
    }

    private void modifyLayout()
    {
        final ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        StaggeredGridLayoutManager.LayoutParams staggerLayoutParams = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
        staggerLayoutParams.setFullSpan(true);
        itemView.setLayoutParams(staggerLayoutParams);
    }
}
