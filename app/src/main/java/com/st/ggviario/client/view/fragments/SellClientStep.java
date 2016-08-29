package com.st.ggviario.client.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.st.dbutil.android.beans.CallbackControler;
import com.st.dbutil.android.model.CallbackClient;
import com.st.dbutil.android.model.ImageTextResource;
import com.st.dbutil.android.model.ItemFragment;
import com.st.ggviario.client.R;
import com.st.ggviario.client.controls.references.RMap;
import com.st.ggviario.client.view.adapters.SupportClient;

/**
 * Created by xdata on 7/26/16.
 */
public class SellClientStep extends AbstractStep implements ItemFragment, CallbackClient
{
    private View viewRoot;
    private Context context;
    private SupportClient support;
    private RecyclerView recicler;

    @Override
    public void onCreate(@Nullable Bundle restore)
    {
        super.onCreate(restore);
        CallbackControler.inOutingNet(this);
        this.context = this.getActivity();
        this.support = new SupportClient(this.context);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle restore)
    {
        this.viewRoot = inflater.inflate(R.layout.step_sell_client, container, false);
        this.recicler = (RecyclerView) this.viewRoot.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false);
        this.recicler.setLayoutManager(layoutManager);
        this.recicler.setAdapter(this.support.getSupport());

        return this.viewRoot;
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
    public void onReceive(SendType sendType, CallbackClient origem, String summary, Object[] values)
    {
        String keys  = (String) origem.getProtocolKey();
        switch (keys)
        {
            case RMap.IDENTIFIER_SELL_SUPPORT:
                intentSupportSell(summary, values);
                return;
        }
    }

    private void intentSupportSell(String summary, Object[] values) {
        if(summary.equals(RMap.SUMMARY_NAVE_PAGER))
        {
        }
    }

    @Override
    public Bundle query(CallbackClient cliente, String queryQuention, Object... inParams)
    {
        Bundle bundle = new Bundle();

        return bundle;
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
