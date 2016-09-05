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
import com.st.dbutil.android.model.ImageTextResource;
import com.st.dbutil.android.model.ItemFragment;
import com.st.ggviario.client.R;
import com.st.ggviario.client.references.RMap;
import com.st.ggviario.client.view.adapters.vholders.SupportSellPayment;

public class SellPayment extends AbstractStep implements ItemFragment, RMap
{
	private View rootView;
	private SupportSellPayment support;
	private RecyclerView recyclerView;
	private Context context;

	@Override
	public void onCreate(@Nullable Bundle restore)
	{
		super.onCreate(restore);
		this.context = this.getActivity();
		this.support = new SupportSellPayment(this.context);
	}

	@Override
	public String name() {
		return this.getTitle().toString();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle restore)
	{
		this.rootView = inflater.inflate(R.layout.layout_sell, container, false);
		this.recyclerView = (RecyclerView) this.rootView.findViewById(R.id.recycler_view);
		LinearLayoutManager llm = new LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false);
		this.recyclerView.setLayoutManager(llm);
		this.recyclerView.setAdapter(this.support.getSupportRecyclerAdapter());
		return rootView;
	}


	@Override
	public CharSequence getTitle()
	{
		return  new ImageTextResource("Cocluir", R.drawable.img_money_subs);
	}

	@Override
	public Fragment getFragment()
	{
		return this;
	}

	@Override
	public CharSequence getProtocolKey() {
		return IDENTIFIER_SELL_PAYMENT;
	}
}
