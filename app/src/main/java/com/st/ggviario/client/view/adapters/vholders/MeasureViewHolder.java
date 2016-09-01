package com.st.ggviario.client.view.adapters.vholders;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.dbutil.android.adapter.SupportRecyclerAdapter;
import com.st.dbutil.android.adapter.ViewPagerAdpter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.view.adapters.SupportCalculator;
import com.st.ggviario.client.view.adapters.dataset.MeasureDataSet;
import com.st.ggviario.client.view.events.OnClickMeasure;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public  class MeasureViewHolder extends BaseRecyclerAdapter.ItemViewHolder
{
    public static final Techniques ENTER_ANIMATION = Techniques.RotateInUpLeft;
    public static final Techniques EXIT_ANIMATION = Techniques.RotateOutDownLeft;

    private final TextView tvMeasureName;
    private final TextView tvCodMeasure;
    private final TextView tvMeasureValue;
    private MeasureDataSet dataValues;
    private OnClickMeasure measureManager;

    private SupportCalculator.OnClickMeasureListener clickListiner;

    public MeasureViewHolder(View itemView, OnClickMeasure measureManager)
    {
        super(itemView);
        this.tvCodMeasure = (TextView) itemView.findViewById(R.id.tv_measure_cod);
        this.tvMeasureName = (TextView) itemView.findViewById(R.id.tv_measure_name);
        this.tvMeasureValue = (TextView) itemView.findViewById(R.id.tv_measure_value);
        this.measureManager = measureManager;
    }

    @Override
    public boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position)
    {
        if(dataSet instanceof  MeasureDataSet)
        {
            this.dataValues = (MeasureDataSet) dataSet;
            if (dataValues.isSelected()) markSelectedMeasure(tvCodMeasure, false);
            else unMarkSelectedMeasure(dataValues, this.tvCodMeasure, false);
            this.tvMeasureName.setText(dataValues.getMeasureName());
            this.tvMeasureValue.setText(dataValues.getValueMeasureForOne());
        }
        return false;
    }

    @Override
    public boolean isClickable(int position) {
        return true;
    }

    @Override
    public void onClink(int position)
    {

        final TextView tvMeasureCod = (TextView) this.itemView.findViewById(R.id.tv_measure_cod);
        this.dataValues.setSelected(!this.dataValues.isSelected());
        if(this.dataValues.isSelected())
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
    }

    public static void unMarkSelectedMeasure(MeasureDataSet data, TextView tvMeasureCod, boolean animate)
    {
        if(animate)
            switchTextWhiteAnimation(tvMeasureCod, data.getBackground(), data.getMeasureCod(), ENTER_ANIMATION, EXIT_ANIMATION);
        else
        {
            tvMeasureCod.setText(data.getMeasureCod());
            tvMeasureCod.setBackgroundResource(data.getBackground());
        }
    }

    public static void markSelectedMeasure(final TextView tvMeasureCod, boolean animate)
    {
        float dp = tvMeasureCod.getContext().getResources().getDisplayMetrics().density;
        int dimen = (int)(24*dp);
        Rect bounds = new Rect(0, 0, dimen, dimen);
        Drawable icon = tvMeasureCod.getContext().getResources().getDrawable(R.drawable.ic_done_white_24dp);
        icon.setBounds(bounds);
        SpannableString image = ViewPagerAdpter.createSpannableString("SELECT", icon, icon.getBounds());

        final int enterBackground = R.drawable.shap_oval_green;
        final CharSequence enterText = image;
        if(animate)
            switchTextWhiteAnimation(tvMeasureCod, enterBackground, enterText, ENTER_ANIMATION, EXIT_ANIMATION);
    }

    public static void switchTextWhiteAnimation(final TextView tvMeasureCod, final int enterBackground,
                                                final CharSequence enterText, final Techniques enterAnimation,
                                                Techniques exitAnimation) {
        try
        {
            //Animação de saida
            YoYo.with(exitAnimation)
                    .duration(450)
                    .withListener(new SupportRecyclerAdapter.SupportAnimatorListener()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //Animação de entrada
                            tvMeasureCod.setText(enterText);
                            tvMeasureCod.setBackgroundResource(enterBackground);
                            YoYo.with(enterAnimation)
                                    .duration(450)
                                    .playOn(tvMeasureCod);
                        }
                    })
                    .playOn(tvMeasureCod);
        }catch (Exception ex)
        {

        }
    }

    public void setClickListiner(SupportCalculator.OnClickMeasureListener clickListiner) {
        this.clickListiner = clickListiner;
    }
}

