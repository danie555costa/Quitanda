package com.st.ggviario.client.view.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.st.dbutil.android.adapter.ListAdapter;
import com.st.dbutil.android.beans.CallbackControler;
import com.st.dbutil.android.model.CallbackClient;
import com.st.dbutil.android.model.ImageTextResource;
import com.st.dbutil.android.model.ItemFragment;
import com.st.dbutil.android.sqlite.DMLite;
import com.st.ggviario.client.R;
import com.st.ggviario.client.references.RMap;
import com.st.ggviario.client.dao.DaoClient;
import com.st.ggviario.client.model.Client;
import com.st.ggviario.client.view.activitys.SellStepperActivity;
import com.st.ggviario.client.view.item.ClienteView;

import java.util.ArrayList;

/**
 * Created by xdata on 7/27/16.
 */
public class SellClientFragment extends Fragment implements ItemFragment, CallbackClient, AdapterView.OnItemClickListener {
    public final static Client DEFAULT_CLIENT = new Client(1, "Anonimo", "", "", "");
    public static final ClienteView DEFAULT_CLIENT_VIEW = new ClienteView(DEFAULT_CLIENT);

    private Context context;
    private View rootView;
    private ListView listClient;
    private ListAdapter adapter;


    private Client client;
    private ClienteView clientView;
    private DaoClient daoClient;


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        CallbackControler.inOutingNet(this);
        this.adapter = new ListAdapter(context);
        if(savedInstanceState == null)
        {
            this.client = DEFAULT_CLIENT;
            this.clientView = DEFAULT_CLIENT_VIEW;
            this.clientView.setSelected(true);
        }
        this.daoClient = new DaoClient(context);
        updateList();
        Log.i("DBA:APP.TEST", "FragementCLienteList -> ON Create");
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle restore)
    {
        super.onCreateView(inflater, container, restore);

        this.rootView = inflater.inflate(R.layout.step_sell_client, container, false);
        if(restore == null)
        {
        }
        else
        {
        }
        this.listClient.setAdapter(adapter);
        this.listClient.setOnItemClickListener(this);
        this.setRetainInstance(true);
        return this.rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle save)
    {
        super.onSaveInstanceState(save);
    }


    private void updateList()
    {
        ArrayList<Client> clientes = this.daoClient.loadClientes();
        this.adapter.clear();
        ClienteView clienteView;
        for(Client cli:clientes)
        {
            if(cli.getId().equals(DEFAULT_CLIENT.getId()))
                clienteView = DEFAULT_CLIENT_VIEW;
            else clienteView = new ClienteView(cli);
            if(this.client != null
                    && this.clientView != null
                    && this.client.getId().equals(cli.getId()))
                clientView.setSelected(true);
            this.adapter.addItem(clienteView);
        }

    }

    @Override
    public CharSequence getTitle()
    {
        return new ImageTextResource("Lista Clientes", R.drawable.img_client_list);
    }

    @Override
    public Fragment getFragment()
    {
        return this;
    }

    @Override
    public CharSequence getProtocolKey()
    {
        return RMap.IDENTIFIER_SELL_CLIENT_LIST;
    }

    @Override
    public void onReceive(SendType sendType, CallbackClient origem, String summary, Object[] values)
    {

        String key = (String) origem.getProtocolKey();
        Log.i("DBA:APP.TEST", getClass().getName()+"-> RECEIVIED INTENT FROM "+key);
        switch (key)
        {
            case RMap.IDENTIFIER_SELL_CLIENT_REGISTER:
                this.updateList();
                return;
            case RMap.IDENTIFIER_SELL_CLIENT_SUPPORT:
                this.intentSupportClient(summary, values);
                return;
        }

        if(origem instanceof ClienteView)
        {
            intentClient((ClienteView) origem, summary, values);
            return;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void intentClient(ClienteView clienteView, String summary, Object[] values)
    {
        if(summary.equals(RMap.SUMMARY_CLIENT_CLICKED))
        {
            int position = (int) values[0];
            View view = (View) values[1];
            Client cli = (Client) values[2];
            if(this.clientView != null)
                this.clientView.setSelected(false);

            if(clienteView.isSelected())
            {
                this.client = cli;
//                this.adapter.moveItemTo(clienteView, 0);
//                if(clienteView != DEFAULT_CLIENT_VIEW)
//                    this.adapter.moveItemTo(DEFAULT_CLIENT_VIEW, 1);
                this.clientView = clienteView;
            }
            else
            {
                this.clientView = DEFAULT_CLIENT_VIEW;
                this.client = DEFAULT_CLIENT;
                this.clientView.setSelected(true);
//                this.adapter.moveItemTo(DEFAULT_CLIENT_VIEW, 0);

            }
            this.adapter.notifyDataSetChanged();
            CallbackControler.sendTo(this, RMap.IDENTIFIER_SELL_SUPPORT, RMap.SUMMARY_CLIENT_CHANGED, this.client);
        }
    }

    private void intentSupportClient(String summary, Object[] values)
    {
        Log.i("DBA:APP.TEST", getClass().getSimpleName()+"-> intent support client summary:\""+summary+"\", values:\""+ DMLite.toText(values)+"\"");
        if(summary.equals(RMap.SUMMARY_NAVE_PAGER))
        {
            boolean in = (boolean) values[0];
            if(in)
            {
                Bundle result = CallbackControler.query(this, RMap.IDENTIFIER_SELL_SUPPORT, RMap.QUERY_CLIENT_SELECTED);
                this.client = (Client) result.getSerializable(SellStepperActivity.CLIENT);
                for(int i=0; i<this.adapter.getCount(); i++)
                {
                    ClienteView aux = (ClienteView) this.adapter.getItemView(i);
                    if(aux.getClient().getId().equals(client.getId()))
                        aux.setSelected(true);
                }
                this.adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public Bundle query(CallbackClient cliente, String queryQuention, Object... inParams)
    {
        Bundle bundle = new Bundle();
        return bundle;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {

//        ClienteView cli = (ClienteView) this.adapter.getItemView(position);
//        Log.e("DBA:APP.TEST", "CLIETE CLICADO "+cli.getClient());
//
//        cli.setSelected(cli.isSelected());
//        if(cli.isSelected())
//        {
//            this.adapter.moveItemTo(cli, 0);
//            if(this.adapter.getCount()>1)
//                this.adapter.moveItemTo(DEFAULT_CLIENT_VIEW, 1);
//            this.client = cli.getClient();
//        }
//        else
//        {
//            DEFAULT_CLIENT_VIEW.setSelected(true);
//            this.adapter.moveItemTo(DEFAULT_CLIENT_VIEW, 0);
//            this.client = DEFAULT_CLIENT;
//        }
//        this.adapter.notifyDataSetChanged();
    }
}
