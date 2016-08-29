package com.st.ggviario.client.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.dbutil.android.model.ItemFragment;
import com.st.ggviario.client.R;

/**
 * Created by Daniel Costa at 8/27/16.
 * Using user computer xdata
 */
public class SellPaymentNow extends Fragment implements ItemFragment
{
    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_sell_payment_now, container, false);
        return this.rootView;
    }

    @Override
    public CharSequence getTitle() {
        return "Agora";
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public CharSequence getProtocolKey()
    {
        return  "YHhsn";
    }
}
