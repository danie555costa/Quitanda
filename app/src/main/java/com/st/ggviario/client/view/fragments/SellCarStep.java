package com.st.ggviario.client.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.st.dbutil.android.model.ImageTextResource;
import com.st.dbutil.android.model.ItemFragment;
import com.st.ggviario.client.R;
import com.st.ggviario.client.references.RColors;
import com.st.ggviario.client.references.RMap;
import com.st.ggviario.client.dao.DaoProduct;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.view.adapters.SupportSellProducts;

import java.util.ArrayList;
import java.util.List;

public class SellCarStep extends AbstractStep implements ItemFragment
{
	public static final String PRODUCT = "PRODUCT";
	private View rootView;
	private SupportSellProducts supportAdapter;
	private RecyclerView recyclerView;
	private DaoProduct daoProduct;
	private List list;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		this.supportAdapter = new SupportSellProducts(this.getContext());
		this.daoProduct = new DaoProduct(this.getContext());
		ArrayList<Product> listProduct = daoProduct.loadProducts(null);
		this.list = supportAdapter.getCreatedSupport().getListDataSet();
		int colorId;

		for(Product product: listProduct)
		{
			colorId = RColors.switchColor(product.getName().charAt(0), 500);
			SupportSellProducts.DataProduct product1 = new SupportSellProducts.DataProduct(colorId, product);
			this.list.add(product1);
		}
	}

	@Override
	public String name() {
		return this.getTitle().toString();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.layout_sell, container, false);
		this.recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
		final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
		layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

		this.recyclerView.setHasFixedSize(true);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);

			}


			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy)
			{
				super.onScrolled(recyclerView, dx, dy);
				int lastCompletPosition = 0;
				int max = -1;
				int row [] = layoutManager.findLastCompletelyVisibleItemPositions(null);
				for (int i: row)
					if(i>max) max = i;

//                lastCompletPosition = layoutManager.findLastCompletelyVisibleItemPosition() ;
//                lastCompletPosition = max;

				if(lastCompletPosition +1 == supportAdapter.getCreatedSupport().getItemCount())
				{

				}

			}
		});

		this.recyclerView.setAdapter(this.supportAdapter.getCreatedSupport());
		return  this.rootView;
	}

	@Override
	public CharSequence getTitle()
	{
		return new ImageTextResource("Carinho", R.drawable.img_product);
	}

	@Override
	public Fragment getFragment()
	{
		return this;
	}

	@Override
	public CharSequence getProtocolKey() {
		return RMap.IDENTIFIER_SELL_PRINCIPAL;
	}
}
