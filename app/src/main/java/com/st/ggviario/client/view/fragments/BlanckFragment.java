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
 * Created by xdata on 8/11/16.
 */
public class BlanckFragment extends Fragment implements ItemFragment
{
    private  String title;
    private View rootview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootview = inflater.inflate(R.layout.fragment_blanc, container, false);
        return  rootview;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public CharSequence getTitle() {
        return "Titulo";
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public CharSequence getProtocolKey() {
        return "LALALAL";
    }
}
