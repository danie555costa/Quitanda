package com.st.ggviario.client.view.adapters.dataset;

import com.st.dbutil.android.adapter.BaseReclyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Client;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class ClientDataSet implements BaseReclyclerAdapter.ItemDataSet
{
    private final Client client;
    public ClientDataSet(Client client)
    {
        this.client = client;
    }

    @Override
    public int getTypeView() {
        return R.layout.item_client;
    }

    public Client getClient() {
        return client;
    }
}
