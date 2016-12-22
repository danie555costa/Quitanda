package st.domain.quitanda.client.view.adapters.vholders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.view.adapters.vfactory.ViewHolderFactory;

import java.util.HashMap;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class SupportAdapter extends BaseRecyclerAdapter {
    private final HashMap<Integer, ViewHolderFactory> mapViewHolder;

    public SupportAdapter(Context context) {
        super(context, null);
        mapViewHolder = new HashMap<>();
    }


    @Override
    protected ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType, LayoutInflater inflater) {
        ViewHolderFactory builder = this.mapViewHolder.get(viewType);
        String viewName = getContext().getResources().getResourceName(viewType);
        Log.i("DBA:APP.TEST", getClass().getSimpleName()+".onCreateViewHolder(viewGroup, "+viewName+", inflater)");
        View view = builder.factoryView(viewGroup, inflater);
        ItemViewHolder itemViewHolder = this.mapViewHolder.get(viewType).factoryViewHolder(view);
        itemViewHolder.getAdapterPosition();
        return  itemViewHolder;
    }

    @Override
    protected void onBindViewHolder(ItemViewHolder holder, ItemDataSet data, int position) {

    }

    public void addViewHolderFactory(ViewHolderFactory viewHolderBuilder) {
        this.mapViewHolder.put(viewHolderBuilder.getViewType(), viewHolderBuilder);
    }


}
