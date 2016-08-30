package com.st.ggviario.client.view.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.st.dbutil.android.model.ImageTextResource;
import com.st.dbutil.android.model.ItemFragment;
import com.st.ggviario.client.R;
import com.st.ggviario.client.references.RMap;
import com.st.ggviario.client.view.activitys.RegisterClientActivity;
import com.st.ggviario.client.view.adapters.SupportClient;

/**
 * Created by Daniel Costa at 7/26/16.
 * Using user computer xdata
 */
public class SellClientStep extends AbstractStep implements ItemFragment
{
    private Context context;
    private SupportClient support;

    @Override
    public void onCreate(@Nullable Bundle restore)
    {
        super.onCreate(restore);
        this.context = this.getActivity();
        this.support = new SupportClient(this.context);
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
        recicler.setAdapter(this.support.getSupport());
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
    public CharSequence getTitle()
    {
        String title = (context == null)? "Cliente": context.getString(R.string.clients);
        return new ImageTextResource(title, R.drawable.img_clients);
    }

    @Override
    public String name() {
        return this.getTitle().toString();
    }


    @Override
    public Fragment getFragment()
    {
        return this;
    }


    @Override
    public CharSequence getProtocolKey()
    {
        return RMap.IDENTIFIER_SELL_CLIENT_SUPPORT;
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
