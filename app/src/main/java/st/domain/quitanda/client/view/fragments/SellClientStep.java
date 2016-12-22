package st.domain.quitanda.client.view.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.dao.DaoClient;
import st.domain.quitanda.client.model.Client;
import st.domain.quitanda.client.model.visitor.SellCollectorVisitor;
import st.domain.quitanda.client.model.builders.ClientBuilder;
import st.domain.quitanda.client.references.RMap;
import st.domain.quitanda.client.util.animator.OnAnimateSelection;
import st.domain.quitanda.client.util.animator.Selectable;
import st.domain.quitanda.client.view.activitys.NewClientActivity;
import st.domain.quitanda.client.view.adapters.vholders.SupportAdapter;
import st.domain.quitanda.client.view.adapters.vfactory.ClientViewHolderFactory;
import st.domain.quitanda.client.view.adapters.dataset.ClientDataSet;
import st.domain.quitanda.client.model.visitor.Collectable;
import st.domain.quitanda.client.view.callbaks.OnActivityResultObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel Costa at 7/26/16.
 * Using user computer xdata
 */
public class SellClientStep extends AbstractStep implements Collectable
{
    private Context context;
    private SupportAdapter support;
    private DaoClient daoClient;
    private Map<Integer, OnActivityResultObserver> map;
    private Client client;


    @Override
    public void onCreate(@Nullable Bundle restore)
    {
        super.onCreate(restore);
        this.context = this.getActivity();
        this.support = new SupportAdapter(this.context);
        this.map = new HashMap<>();

        //Acao depois de registrar o cliente
        this.map.put(RMap.RESP_REG_CLIENT, new OnActivityResultObserver() {
            @Override
            public void onResult(Activity activity, Intent intent) {
                Bundle bundle = intent.getExtras();
                String clientXML = bundle.getString(RMap.CLIENT);
                ClientBuilder clientBuilder = new ClientBuilder();
                assert clientXML != null;
                client = clientBuilder.buildFromXML(clientXML);
            }

            @Override
            public void onResultEmpty(Activity activity) {}

        });

        ClientViewHolderFactory clientFactory;
        this.support.addViewHolderFactory(clientFactory = new ClientViewHolderFactory());
        clientFactory.getAnimatorManager().addOnAnimateSelection(new OnAnimateSelection() {
            @Override
            public void onPreAnimate(BaseRecyclerAdapter.ItemViewHolder itemViewHolder, Selectable selectable){}

            @Override
            public void onPosAnimate(BaseRecyclerAdapter.ItemViewHolder itemViewHolder, Selectable selectable)
            {
                if (!(selectable instanceof ClientDataSet)) throw new AssertionError();
                client = ((ClientDataSet) selectable).getClient();
            }
        });


        this.daoClient = new DaoClient(context);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                ArrayList<Client> list = daoClient.loadClientes();
                for(Client client: list)
                {
                    support.addDataSet(new ClientDataSet(client));
                }
            }
        });
        thread.start();
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle restore)
    {
        View viewRoot = inflater.inflate(R.layout.step_sell_client, container, false);
        RecyclerView recicler = (RecyclerView) viewRoot.findViewById(R.id.recycler_view);
        FloatingActionButton floatingActionButton = (FloatingActionButton) viewRoot.findViewById(R.id.fab_new_client);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellClientStep.this.getContext(), NewClientActivity.class);
                startActivityForResult(intent, RMap.REQ_REG_CLIENT);
            }
        });

        recicler.setLayoutManager(layoutManager);
        recicler.setAdapter(this.support);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                openRegisterClient();
            }
        });

        return viewRoot;
    }

    private void openRegisterClient() {
        Intent intent = new Intent(getContext(), NewClientActivity.class);
        startActivityForResult(intent, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RMap.REQ_REG_CLIENT
                && requestCode == RMap.RESP_REG_CLIENT
                && data != null) {
            Bundle bundle =data.getExtras();

        }
    }

    @Override
    public String name() {
        return "Cliente";
    }


    @Override
    public boolean isOptional() {
        return true;
    }

    @Override
    public String optional() {
        return "Opcional";
    }

    @Override
    public void onNext() {
    }

    @Override
    public void accept(SellCollectorVisitor collectorVisitor) {
        collectorVisitor.collectClient(this.client);
    }
}
