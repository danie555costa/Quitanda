package st.domain.quitanda.client.view.events;

import st.domain.quitanda.client.view.adapters.dataset.MeasureDataSet;

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
