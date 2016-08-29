package com.st.ggviario.client.view.adapters;

import android.content.Context;
import android.view.View;

import com.st.dbutil.android.adapter.BaseReclyclerAdapter;
import com.st.dbutil.android.adapter.SupportRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Client;
import com.st.ggviario.client.view.adapters.dataset.ClientDataSet;
import com.st.ggviario.client.view.adapters.vholders.ClientViewHolder;

import java.util.List;

/**
 * Created by Daniel Costa on 8/20/16.
 * User cumputer: xdata
 */
public class SupportClient implements SupportRecyclerAdapter.OnCreateViewHolder {
    private final Context context;
    private final List<BaseReclyclerAdapter.ItemDataSet> list;
    private SupportRecyclerAdapter support;

    public SupportClient(Context context)
    {
        this.support = new SupportRecyclerAdapter(context);
        this.support.useTypeViewAsReferenceLayout(true);
        this.support.setOnCreateViewHolder(this);
        this.context = context;
        this.list = support.getListDataSet();

        this.list.add(new ClientDataSet(new Client(1, "Marcos", "Conceção", "Santana", "992033")));
        this.list.add(new ClientDataSet(new Client(1, "Marcos", "Conceção", "Santana", "992033")));
        this.list.add(new ClientDataSet(new Client(1, "Marcos", "Conceção", "Santana", "992033")));
        this.list.add(new ClientDataSet(new Client(1, "Marcos", "Conceção", "Santana", "992033")));
        this.list.add(new ClientDataSet(new Client(1, "Marcos", "Conceção", "Santana", "992033")));
    }

    @Override
    public BaseReclyclerAdapter.ItemViewHolder onCreateViewHolder(View view, int viewType, int onRecyclerViewId) {
        switch (viewType)
        {
            case R.layout.item_client: return new ClientViewHolder(view);
        }
        return null;
    }

    public SupportRecyclerAdapter getSupport() {
        return support;
    }

}
