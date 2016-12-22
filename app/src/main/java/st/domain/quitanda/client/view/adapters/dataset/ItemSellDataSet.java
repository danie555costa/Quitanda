package st.domain.quitanda.client.view.adapters.dataset;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.model.ItemSell;
import st.domain.quitanda.client.util.FormatterFactory;

import java.text.NumberFormat;

/**
 * Created by Daniel Costa at 9/3/16.
 * Using user computer xdata
 */
public class ItemSellDataSet implements BaseRecyclerAdapter.ItemDataSet {

    private final NumberFormat formatterMoney;
    private final NumberFormat formatterQuantity;
    private ItemSell itemSell;

    public ItemSellDataSet(ItemSell itemSell) {
        this.itemSell = itemSell;
        FormatterFactory fmtFactory = new FormatterFactory();
        this.formatterMoney = fmtFactory.instanceFormatterMoney();
        this.formatterQuantity = fmtFactory.instanceFormatterQuantity();
    }

    @Override
    public int getTypeView() {
        return R.layout.item_sell;
    }

    public CharSequence getProduct() {
        return this.itemSell.getProduct();
    }

    public String getQuantity ()
    {
        return this.itemSell.getUsedQuantity()+" "+itemSell.getMeasure().getKey();
    }

    public String getAmount ()
    {
        return this.formatterMoney.format(this.itemSell.getAmountPay());
    }
}
