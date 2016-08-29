package com.st.ggviario.client.view.adapters.vholders;

import android.view.View;
import android.widget.TextView;

import com.st.dbutil.android.adapter.BaseReclyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.view.adapters.dataset.ClientDataSet;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class ClientViewHolder extends BaseReclyclerAdapter.ItemViewHolder
{

    private final TextView tvClientName;
    private final TextView tvClientResidence;
    private final TextView tvClientContact;
    private ClientDataSet clientDataSet;

    public ClientViewHolder(View itemView)
    {
        super(itemView);
        this.tvClientName = (TextView) itemView.findViewById(R.id.tv_client_name);
        this.tvClientResidence = (TextView) itemView.findViewById(R.id.tv_client_residence);
        this.tvClientContact = (TextView) itemView.findViewById(R.id.tv_client_contact);
    }

    @Override
    public boolean bind(BaseReclyclerAdapter.ItemDataSet itemDataSet, int position) {
        if(itemDataSet instanceof ClientDataSet){
            this.clientDataSet = (ClientDataSet) itemDataSet;
            this.tvClientName.setText(clientDataSet.getClient());
            this.tvClientContact.setText(clientDataSet.getClient().getContact());
            this.tvClientResidence.setText(clientDataSet.getClient().getResidence());
        }
        return true;
    }


}
