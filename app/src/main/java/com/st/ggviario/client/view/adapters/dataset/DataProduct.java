package com.st.ggviario.client.view.adapters.dataset;

import com.st.dbutil.android.adapter.BaseReclyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.model.template.BaseCharacter;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class DataProduct extends BaseCharacter implements BaseReclyclerAdapter.ItemDataSet
{
    private int idColor;
    private Product product;
    public boolean efeito;

    public DataProduct(int idColor, Product product)
    {
        this.idColor = idColor;
        this.product = product;
        this.efeito = false;
    }

    @Override
    public String toString()
    {
        return product.toString();
    }

    @Override
    public int getTypeView() {
        return R.layout.item_cart_operation;
    }

    public Product getProduct() {
        return product;
    }

    public int getIdColor() {
        return idColor;
    }
}