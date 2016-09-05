package com.st.ggviario.client.view.adapters.vfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.view.adapters.vholders.MeasureViewHolder;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class MeasureViewHolderFactory implements ViewHolderFactory, MeasureViewHolder.onChangeMeasure, MeasureViewHolder.OnBindListener {
    private MeasureViewHolder.OnClickMeasureListener onClickMeasureListener;
    private MeasureViewHolder.onChangeMeasure onSwithcMeasure;
    private MeasureViewHolder currentMeasureViewHolder;

    @Override
    public BaseRecyclerAdapter.ItemViewHolder factoryViewHolder(View view) {
        MeasureViewHolder measureViewHolder = new MeasureViewHolder(view, this);
        measureViewHolder.setOnClickMeasureListener(this.onClickMeasureListener);
        measureViewHolder.setOnBindListener(this);
        return measureViewHolder;
    }

    public MeasureViewHolderFactory setOnClickMeasureListener(MeasureViewHolder.OnClickMeasureListener onClickMeasureListener) {
        this.onClickMeasureListener = onClickMeasureListener;
        return this;
    }

    @Override
    public View factoryView(ViewGroup viewGroup, LayoutInflater inflater) {
        return inflater.inflate(getViewType(), viewGroup, false);
    }

    @Override
    public int getViewType() {
        return R.layout.item_measure;
    }

    @Override
    public void onChange(MeasureViewHolder newMeasureViewHolder) {
        this.currentMeasureViewHolder = newMeasureViewHolder;
    }

    @Override
    public MeasureViewHolder getChange() {
        return this.currentMeasureViewHolder;
    }

    @Override
    public void onBind(MeasureViewHolder measureViewHolder) {
        if(measureViewHolder.getValue().isSelected())
            this.currentMeasureViewHolder = measureViewHolder;
    }
}
