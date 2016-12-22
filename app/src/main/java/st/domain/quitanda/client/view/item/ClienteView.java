package st.domain.quitanda.client.view.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import st.domain.support.android.beans.CallbackControler;
import st.domain.support.android.model.CallbackClient;
import st.domain.support.android.model.ItemView;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.references.RMap;
import st.domain.quitanda.client.model.Client;

/**
 * Created by xdata on 7/27/16.
 */
public class ClienteView implements ItemView, CallbackClient, RMap, View.OnClickListener {
    private Client client;
    private View viewRoot;
    private TextView tvClienteName;
    private TextView tvClienteMorada;
    private TextView tvClienteContact;
    private boolean selected;
    private int position;

    public  ClienteView() {
    }

    public  ClienteView(Client client)
    {
        this.client = client;

    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    @Override
    public View createView(int position, LayoutInflater inflater, View view, ViewGroup viewGroup)
    {
        if(this.selected)
            this.viewRoot = inflater.inflate(R.layout.item_client_selected, viewGroup, false);
        else this.viewRoot = inflater.inflate(R.layout.item_client_unseleted, viewGroup, false);
        this.tvClienteName = (TextView) this.viewRoot.findViewById(R.id.tvClienteName);
        this.tvClienteContact = (TextView) this.viewRoot.findViewById(R.id.tvClientContact);
        this.tvClienteMorada = (TextView) this.viewRoot.findViewById(R.id.tvClinetMorada);
        this.position = position;


        this.viewRoot.setOnClickListener(this);
        this.tvClienteName.setText(client);
        this.tvClienteContact.setText(this.client.getContact());
        this.tvClienteMorada.setText(this.client.getResidence());

        return this.viewRoot;
    }

    @Override
    public Object getObject() {
        return client;
    }

    @Override
    public void onReceive(SendType sendType, CallbackClient origem, String summary, Object[] values) {

    }

    @Override
    public Bundle query(CallbackClient cliente, String queryQuention, Object... inParams) {
        Bundle bundle = new Bundle();
        return bundle;
    }


    @Override
    public CharSequence getProtocolKey()
    {
        return client.getId()+"@"+RMap.IDENTIFIER_SELL_CLIENT_ITEM;
    }

    public Client getClient() {
        return client;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void onClick(View view)
    {
        this.selected = !selected;
        CallbackControler.sendTo(this, IDENTIFIER_SELL_CLIENT_LIST, RMap.SUMMARY_CLIENT_CLICKED, this.position, this.viewRoot, this.client);
    }
}
