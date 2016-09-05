package com.st.ggviario.client.view.fragments;


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
import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.dao.DaoClient;
import com.st.ggviario.client.model.Client;
import com.st.ggviario.client.util.animator.OnAnimateSelection;
import com.st.ggviario.client.util.animator.Selectable;
import com.st.ggviario.client.view.activitys.RegisterClientActivity;
import com.st.ggviario.client.view.adapters.vholders.ClientViewHolder;
import com.st.ggviario.client.view.adapters.vholders.SupportAdapter;
import com.st.ggviario.client.view.adapters.vfactory.ClientViewHolderFactory;
import com.st.ggviario.client.view.adapters.dataset.ClientDataSet;

import java.util.ArrayList;

/**
 * Created by Daniel Costa at 7/26/16.
 * Using user computer xdata
 */
public class SellClientStep extends AbstractStep
{
    private Context context;
    private SupportAdapter support;
    private DaoClient daoClient;
    private ClientViewHolder clientViewHolder;

    @Override
    public void onCreate(@Nullable Bundle restore)
    {
        super.onCreate(restore);
        this.context = this.getActivity();
        this.support = new SupportAdapter(this.context);
        ClientViewHolderFactory clientViewHolderFactory;
        this.support.addViewHolderFactory(clientViewHolderFactory = new ClientViewHolderFactory());
        clientViewHolderFactory.getAnimatorManager()
                .addOnAnimateSelection(new OnAnimateSelection() {
                    @Override
                    public void onPreAnimate(BaseRecyclerAdapter.ItemViewHolder itemViewHolder, Selectable selectable) {
                        clientViewHolder = (ClientViewHolder) itemViewHolder;
                    }

                    @Override
                    public void onPosAnimate(BaseRecyclerAdapter.ItemViewHolder itemViewHolder, Selectable selectable) {
                        if(clientViewHolder.isSeleted())
                            SellClientStep.super.onNext();
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
        Intent intent = new Intent(getContext(), RegisterClientActivity.class);
        startActivityForResult(intent, 10);
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
}
