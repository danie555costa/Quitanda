package st.domain.quitanda.client.view.adapters.vholders;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.support.android.adapter.SupportRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.view.activitys.PaymentModeActivity;

/**
 * Created by Daniel Costa at 8/26/16.
 * Using user computer xdata
 */
public class ModePayment extends BaseRecyclerAdapter.ItemViewHolder implements SupportRecyclerAdapter.OnCreateViewHolder, View.OnClickListener {

    private final RecyclerView recyclerView;
    private final Button btChagePayMode;
    private SupportRecyclerAdapter supportAdapterPayInfo;

    public ModePayment(View itemView)
    {
        super(itemView);
        this.recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_pay_info);
        this.btChagePayMode = (Button) this.itemView.findViewById(R.id.bt_pay_mode_change);
        this.btChagePayMode.setOnClickListener(this);
    }


    @Override
    public BaseRecyclerAdapter.ItemViewHolder onCreateViewHolder(View view, int viewType, int onRecyclerViewId) {
        switch (viewType)
        {
            case R.layout.item_detais:
                ListItemView.ItemViewHolder listItemViewHolder = new ListItemView.ItemViewHolder(view, R.id.item_title);
                listItemViewHolder.setTextSecond1dId(R.id.item_value);
                return listItemViewHolder;
        }
        return null;
    }

    @Override
    public boolean bind(BaseRecyclerAdapter.ItemDataSet itemDataSet, int position)
    {
        super.bind(itemDataSet, position);
        if(supportAdapterPayInfo == null)
        {
            this.supportAdapterPayInfo = new SupportRecyclerAdapter(super.getContext());
            this.supportAdapterPayInfo.setOnCreateViewHolder(this);
            this.supportAdapterPayInfo.useTypeViewAsReferenceLayout(true);


            // O valor não devera ser colocado nessa area (sera colocado apenas para o teste) O ideal e colocar o valor no momento de OnBind

            ListItemView.TextTowLineDataSet dataSet;
            supportAdapterPayInfo.addDataSet(dataSet = new ListItemView.TextTowLineDataSet(R.layout.item_detais));
            dataSet.setTextPrimary("Cliente");
            dataSet.setTextSecond1("Anonimo");

            supportAdapterPayInfo.addDataSet(dataSet = new ListItemView.TextTowLineDataSet(R.layout.item_detais));
            dataSet.setTextPrimary("Pagamento");
            dataSet.setTextSecond1("Credito");

            supportAdapterPayInfo.addDataSet(dataSet = new ListItemView.TextTowLineDataSet(R.layout.item_detais));
            dataSet.setTextPrimary("Data entrega");

            supportAdapterPayInfo.addDataSet(dataSet = new ListItemView.TextTowLineDataSet(R.layout.item_detais));
            dataSet.setTextPrimary("Data pagamento");
            dataSet.setTextSecond1("30/10/2016");


            RecyclerView.LayoutManager lm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            this.recyclerView.setLayoutManager(lm);
            this.recyclerView.setAdapter(this.supportAdapterPayInfo);
        }
        return true;
    }

    @Override
    public void onClick(View view)
    {
        if(view == btChagePayMode)
        {
            Intent intent = new Intent(getContext(), PaymentModeActivity.class);
            getContext().startActivity(intent);
        }
    }
}

