package st.domain.quitanda.client.view.adapters.vholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.support.android.adapter.SupportRecyclerAdapter;
import st.domain.quitanda.client.R;

import java.util.List;

/**
 * Created by Daniel Costa on 8/21/16.
 * User computer: xdata
 */
public class SupportSellPayment implements SupportRecyclerAdapter.OnCreateViewHolder, SupportRecyclerAdapter.OnPosViewCreated {
    private final Context context;
    private final List<BaseRecyclerAdapter.ItemDataSet> listItems;
    private final PaymentModeDataSet dataPayment;
    private final ListItemSellPaymentDataSet listProducteSelected;

    private SupportRecyclerAdapter supportRecyclerAdapter;

    public SupportSellPayment(Context context)
    {
        this.supportRecyclerAdapter = new SupportRecyclerAdapter(context);

        this.context = context;
        this.listItems = this.supportRecyclerAdapter.getListDataSet();
        this.supportRecyclerAdapter.useTypeViewAsReferenceLayout(true);
        this.listItems.add(this.dataPayment = new PaymentModeDataSet());
        this.listItems.add(this.listProducteSelected = new ListItemSellPaymentDataSet());
        this.supportRecyclerAdapter.setOnCreateViewHolder(this);
        this.supportRecyclerAdapter.setOnPosViewCreated(this);
    }

    @Override
    public BaseRecyclerAdapter.ItemViewHolder onCreateViewHolder(View view, int viewType, int onRecyclerViewId)
    {
        switch (viewType)
        {
            case R.layout.item_group_payment_mode: return new ModePayment(view);
            case R.layout.item_group_payment_products: return new ListItemSellPayment(view);
        }
        return null;
    }

    public RecyclerView.Adapter getSupportRecyclerAdapter()
    {
        return this.supportRecyclerAdapter;
    }

    @Override
    public void onViewCreated(View view, ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case R.layout.item_sell:
                view.findViewById(R.id.bt_remove).setVisibility(View.GONE);
        }
    }


    public class PaymentModeDataSet implements BaseRecyclerAdapter.ItemDataSet
    {
        private TypePayment typePayment;

        @Override
        public int getTypeView() {
            return R.layout.item_group_payment_mode;
        }
    }

    public class ListItemSellPaymentDataSet implements BaseRecyclerAdapter.ItemDataSet
    {
        @Override
        public int getTypeView()
        {
            return R.layout.item_group_payment_products;
        }
    }


    public  enum TypePayment
    {
        NOW,
        CREDIT,
        QUEUE
    }
}
