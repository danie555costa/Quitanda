package com.st.ggviario.client.view.adapters.dataset;

import com.st.dbutil.android.adapter.BaseReclyclerAdapter;
import com.st.ggviario.client.model.Product;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class ProductDataSet implements BaseReclyclerAdapter.ItemDataSet
{

    private final Product product;

    public ProductDataSet(Product product)
    {
        this.product = product;
    }

    @Override
    public int getTypeView() {
        return 0;
    }
}
