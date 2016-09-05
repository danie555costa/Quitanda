package com.st.ggviario.client.view.adapters.vholders;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
import com.st.ggviario.client.view.adapters.dataset.MeasureDataSet;

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
    private MeasureDataSet measureDataSet;
    private final onChangeMeasure onChangeMeasure;

    private OnClickMeasureListener onClickMeasureListener;
    private OnBindListener onBindListener;

    public MeasureViewHolder(View itemView, MeasureViewHolder.onChangeMeasure onChangeMeasure)
    {
        super(itemView);
        this.onChangeMeasure = onChangeMeasure;
        this.tvCodMeasure = (TextView) itemView.findViewById(R.id.tv_measure_cod);
        this.tvMeasureName = (TextView) itemView.findViewById(R.id.tv_measure_name);
        this.tvMeasureValue = (TextView) itemView.findViewById(R.id.tv_measure_value);
    }

    @Override
    public boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position)
    {
        if(dataSet instanceof  MeasureDataSet)
        {
            this.measureDataSet = (MeasureDataSet) dataSet;
            if (measureDataSet.isSelected()) markSelectedMeasure(tvCodMeasure, false);
            else unMarkSelectedMeasure(measureDataSet, this.tvCodMeasure, false);
            this.tvMeasureName.setText(measureDataSet.getMeasureName());
            this.tvMeasureValue.setText(measureDataSet.getValueMeasureForOne());
        }
        this.onBindListener.onBind(this);
        return true;
    }

    @Override
    public boolean isClickable(int position) {
        return true;
    }

    public void setSelected(boolean selected) {
        this.measureDataSet.setSelected(selected);
        if(selected) markSelectedMeasure(this.tvCodMeasure, true);
        else unMarkSelectedMeasure(this.measureDataSet, this.tvCodMeasure, true);
    }

    @Override
    public void onClink(int position)
    {
        MeasureViewHolder current = this.onChangeMeasure.getChange();
        this.measureDataSet.setSelected(!this.measureDataSet.isSelected());

        if(this.measureDataSet.isSelected())
        {
            this.onChangeMeasure.onChange(this);
        }
        else this.onChangeMeasure.onChange(null);

        if(current != null) current.setSelected(false);

        this.setSelected(this.measureDataSet.isSelected());

        if(onClickMeasureListener != null)
            this.onClickMeasureListener.onClickMeasure(this.itemView, this.measureDataSet);
    }

    public void unMarkSelectedMeasure(MeasureDataSet data, TextView tvMeasureCod, boolean animate)
    {
        if(animate)
            switchTextWhiteAnimation(tvMeasureCod, data.getBackground(), data.getMeasureCod(), ENTER_ANIMATION, EXIT_ANIMATION);
        else
        {
            tvMeasureCod.setText(data.getMeasureCod());
            tvMeasureCod.setBackgroundResource(data.getBackground());
        }
    }

    public void markSelectedMeasure(final TextView tvMeasureCod, boolean animate)
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
        else {
            tvMeasureCod.setText(image);
            tvMeasureCod.setBackgroundResource(enterBackground);
        }

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

    public void setOnClickMeasureListener(OnClickMeasureListener onClickMeasureListener) {
        this.onClickMeasureListener = onClickMeasureListener;
    }

    public void setOnBindListener(OnBindListener onBindListener) {
        this.onBindListener = onBindListener;
    }

    public MeasureDataSet getValue() {
        return this.measureDataSet;
    }

    public interface OnClickMeasureListener
    {
        void onClickMeasure(View view, MeasureDataSet dataMeasure);
    }

    public interface onChangeMeasure {

        void onChange(MeasureViewHolder newMeasureViewHolder);

        MeasureViewHolder getChange();
    }

    public interface OnBindListener {
        void onBind(MeasureViewHolder measureViewHolder);
    }
}

