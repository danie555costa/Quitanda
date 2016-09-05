package com.st.ggviario.client.view.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.dbutil.android.model.ItemFragment;
import com.st.ggviario.client.R;
import com.st.ggviario.client.references.RMap;
import com.st.ggviario.client.view.activitys.Harvest;
import com.st.ggviario.client.view.activitys.RegisterClientActivity;
import com.st.ggviario.client.view.activitys.SellStepperActivity;
import com.st.ggviario.client.view.adapters.vholders.SupportHome;

import java.util.List;

//import ivb.com.materialstepper.stepperFragment;

/**
 * Created by xdata on 8/11/16.
 */
public class MainHome extends Fragment implements ItemFragment
{
    private View rootView;
    private RecyclerView recyclerView;
    private SupportHome supportAdapter;
    private List list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.supportAdapter = new SupportHome(this.getContext());
        SupportHome.DataOperation sell = new SupportHome.DataOperation(R.color.mat_indigo_primary, "Vendas", R.drawable.ic_shopping_cart_white_48dp);
        SupportHome.DataOperation sync = new SupportHome.DataOperation(R.color.mat_green_primary, "Sincronização", R.drawable.ic_sync_white_48dp);
        SupportHome.DataOperation harvest = new SupportHome.DataOperation(R.color.mat_pink_primary, "Colheitas", R.drawable.ic_shopping_basket_white_48dp);

        this.list = supportAdapter.getCreatedSupport().getListDataSet();
        sell.setClassManager(SellStepperActivity.class);
        sync.setClassManager(RegisterClientActivity.class);
        harvest.setClassManager(Harvest.class);

        this.list.add(sell);
        this.list.add(sync);
        this.list.add(harvest);
        this.list.add(sell);
        this.list.add(sync);
        this.list.add(harvest);
        this.list.add(sell);
        this.list.add(sync);
        this.list.add(harvest);
        this.list.add(sell);
        this.list.add(sync);
        this.list.add(harvest);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.layout_recicler_view, container, false);
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        int columns = (this.getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                ? 3: 2;

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(columns, LinearLayoutManager.VERTICAL);
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
        return this.rootView;
    }

    @Override
    public CharSequence getTitle()
    {
        return "Inicio";
    }

    @Override
    public Fragment getFragment()
    {
        return this;
    }
    
    
    @Override
    public CharSequence getProtocolKey() {
        return RMap.IDENTIFIER_MAIN_HOME;
    }

//    @Override
//    public boolean onNextButtonHandler() {
//        return true;
//    }
}
