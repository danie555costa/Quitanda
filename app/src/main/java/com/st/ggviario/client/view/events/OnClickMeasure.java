package com.st.ggviario.client.view.events;

import com.st.ggviario.client.view.adapters.dataset.MeasureDataSet;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class OnClickMeasure
{
    private MeasureDataSet measure;

    public void newMeasure(MeasureDataSet measure)
    {
        if(measure != null)
            this.measure.setSelected(false);
        this.measure = measure;
    }
}
