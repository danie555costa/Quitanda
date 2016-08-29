package com.st.ggviario.client.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.st.dbutil.android.adapter.BaseReclyclerAdapter;
import com.st.dbutil.android.adapter.SupportRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Measure;
import com.st.ggviario.client.view.adapters.dataset.CalculatorDataSet;
import com.st.ggviario.client.view.adapters.dataset.MeasureDataSet;
import com.st.ggviario.client.view.adapters.vholders.CalculatorViewHolder;
import com.st.ggviario.client.view.adapters.vholders.MeasureViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Costa on 8/11/16.
 * User computer: xdata
 */
public class SupportCalculator implements  SupportRecyclerAdapter.OnCreateViewHolder, SupportRecyclerAdapter.OnItemClickListener {

    private static final int TYPE_KEYBOARD = R.layout.item_calculator;
    private static final int TYPE_MEASURE = R.layout.item_measure;

    private final SupportRecyclerAdapter support;
    private final List<BaseReclyclerAdapter.ItemDataSet> list;
    private final CalculatorDataSet calc;
    private OnClickKeyboarListner onClickKeyboarListner;
    private MeasureDataSet currentDadaMeasure;
    private OnClickMeasureListener onCLickMeasureListener;

    public SupportCalculator(Context context) {
        this.support = new SupportRecyclerAdapter(context);
        this.list = support.getListDataSet();

        this.calc = new CalculatorDataSet(12);
        this.list.add( calc );

        this.support.setOnCreateViewHolder(this);
        this.support.useTypeViewAsReferenceLayout(false);
        this.support.useTypeViewAsReferenceLayout(true);
        this.support.setOnItemClickListener(this);
    }

    public void setListMeasure(ArrayList<Measure> dataList)
    {
        for(Measure itemMeasure: dataList)
            this.list.add(new MeasureDataSet(itemMeasure));
    }

    public SupportRecyclerAdapter getCreatedSupport()
    {
        return this.support;
    }

    @Override
    public BaseReclyclerAdapter.ItemViewHolder onCreateViewHolder(View view, int viewType, int onRecyclerViewId)
    {
        BaseReclyclerAdapter.ItemViewHolder holder;
        if(viewType == TYPE_KEYBOARD)
        {
            CalculatorViewHolder calcHolder = new CalculatorViewHolder(view);
            calcHolder.setOnclickListiner(this.onClickKeyboarListner);
            holder = calcHolder;
        }
        else
        {
            MeasureViewHolder measureHolder = new MeasureViewHolder(view);
            measureHolder.setClickListiner(this.onCLickMeasureListener);
            holder = measureHolder;
        }

//        Steppers s;
        return holder;
    }

    public void setOnClickKeyboardListner(OnClickKeyboarListner onClickKeyboarListner) {
        this.onClickKeyboarListner = onClickKeyboarListner;
    }

    @Override
    public void onItemClick(View view, BaseReclyclerAdapter.ItemDataSet dataSet, int adapterPosition, int viewPosition) {
        if(dataSet.getTypeView() == TYPE_MEASURE)
        {
            MeasureDataSet dataMeasure = (MeasureDataSet) dataSet;
            if(currentDadaMeasure == dataMeasure)
                currentDadaMeasure = null;

            final TextView tvMeasureCod = (TextView) view.findViewById(R.id.tv_measure_cod);
            dataMeasure.setSelected(!dataMeasure.isSelected());
            if(dataMeasure.isSelected())
            {
                MeasureViewHolder.markSelectedMeasure(tvMeasureCod, true);
                if(currentDadaMeasure != null)
                {
                    currentDadaMeasure.setSelected(false);
                    RecyclerView.ViewHolder itemHolder = support.getViewHolderIfAvailable(currentDadaMeasure);
                    if(itemHolder != null && itemHolder.itemView != null)
                    {
                        View itemView = itemHolder.itemView;
                        TextView tvMeasureOldSelectio = (TextView) itemView.findViewById(R.id.tv_measure_cod);
                        MeasureViewHolder.switchTextWhiteAnimation(tvMeasureOldSelectio,
                                currentDadaMeasure.getBackground(),
                                currentDadaMeasure.getMeasureCod(),
                                MeasureViewHolder.ENTER_ANIMATION,
                                MeasureViewHolder.EXIT_ANIMATION);
                    }
                }
                this.currentDadaMeasure = dataMeasure;
            }
            else MeasureViewHolder.unMarkSelectedMeasure(dataMeasure, tvMeasureCod, true);

            if(this.onCLickMeasureListener != null)
                this.onCLickMeasureListener.onClickMeasure(view, this.currentDadaMeasure);
        }
    }


    public void setOnclickMeasureListener(OnClickMeasureListener onClickMeasureListener) {
        this.onCLickMeasureListener = onClickMeasureListener;
    }

    public MeasureDataSet getCurrentDataMeasure() {
        return this.currentDadaMeasure;
    }

    public double getKeyboardValue() {
        return ((CalculatorDataSet)this.list.get(0)).getValue();
    }

    public void setPrice(double price)
    {
        this.calc.setPreviewPrice(price);
        CalculatorViewHolder holder = (CalculatorViewHolder) this.support.getViewHolderIfAvailable(this.calc);
        holder.valueChaged();
    }



    public interface OnClickKeyboarListner
    {
        void onClick(View view, char key, CalculatorDataSet data);

        void onClickAction(View view, KeyboardAction action, CalculatorDataSet data);
    }


    public enum KeyboardAction
    {
        BACKSPACE,
        CLEAR
    }


    public interface OnClickMeasureListener
    {

        void onClickMeasure(View view, MeasureDataSet dataMeasure);
    }
}
