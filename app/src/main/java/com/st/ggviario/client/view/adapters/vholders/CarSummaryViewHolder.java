package com.st.ggviario.client.view.adapters.vholders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.dbutil.android.adapter.SupportRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.model.builders.ProductBuilder;
import com.st.ggviario.client.view.adapters.dataset.DataProduct;

import java.util.ArrayList;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class CarSummaryViewHolder extends BaseRecyclerAdapter.ItemViewHolder
{
    private final SupportRecyclerAdapter supportProductCar;
    RecyclerView recyclerView;
    public CarSummaryViewHolder(View view)
    {
        super(view);
        ArrayList<BaseRecyclerAdapter.ItemDataSet> lista = new ArrayList<>();
        ProductBuilder builder = new ProductBuilder();
        Product product;

        lista.add(new DataProduct(0,  builder.id(10).name("Ovos").measure("10").build()));
        lista.add(new DataProduct(0, builder.id(11).name("Castanha").measure("10").build()));
        lista.add(new DataProduct(0, builder.id(213).name("Ope").measure("10").build()));
        lista.add(new DataProduct(0, builder.id(89).name("Livros").measure("10").build()));

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_car_products);

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        this.supportProductCar = new SupportRecyclerAdapter(view.getContext(), lista, R.layout.item_product_selected);
        //this.supportProductCar.setOnCreateViewHolder(SupportSellProducts.this);
        this.recyclerView.setAdapter(this.supportProductCar);

        modifyLayout();
    }

    private void modifyLayout()
    {
        final ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        StaggeredGridLayoutManager.LayoutParams staggerLayoutParams = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
        staggerLayoutParams.setFullSpan(true);
        itemView.setLayoutParams(staggerLayoutParams);
    }
}
