package st.domain.quitanda.client.view.adapters.dataset;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.model.Product;
import st.domain.quitanda.client.util.BaseCharacter;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class ProductDataSet extends BaseCharacter implements BaseRecyclerAdapter.ItemDataSet
{
    private int idColor;
    private Product product;
    public boolean efeito;

    public ProductDataSet(int idColor, Product product)
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
        return R.layout.item_product;
    }

    public Product getProduct() {
        return product;
    }

    public int getIdColor() {
        return idColor;
    }
}