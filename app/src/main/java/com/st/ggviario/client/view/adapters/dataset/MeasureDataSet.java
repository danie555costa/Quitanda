package com.st.ggviario.client.view.adapters.dataset;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.dbutil.android.model.Money;
import com.st.ggviario.client.R;
import com.st.ggviario.client.references.RColors;
import com.st.ggviario.client.model.Measure;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class MeasureDataSet  implements BaseRecyclerAdapter.ItemDataSet
{
    private final Measure measuere;
    private boolean selected;
    private int background;
    private boolean usedEfect;

    public MeasureDataSet(Measure measuere)
    {
        this.measuere = measuere;
        this.selected = false;

        this.background = RColors.switchBackgroundOvalShap(measuere.charAt(0),
                R.drawable.shap_oval_primary,
                R.drawable.shap_oval_teal,
                R.drawable.shap_oval_green
        );
    }
    @Override
    public String toString()
    {
        return measuere.toString();
    }

    public Measure getMeasure() {
        return measuere;
    }

    @Override
    public int getTypeView() {
        return R.layout.item_measure;
    }

    public int getIdMetreage() {
        return this.measuere.getId();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getMeasureCod() {
        return this.measuere.getKey();
    }

    public CharSequence getMeasureName() {
        return this.measuere.getName();
    }

    public int getBackground() {
        return background;
    }

    public boolean isUsedEfect() {
        return usedEfect;
    }

    public void setUsedEfect(boolean usedEfect) {
        this.usedEfect = usedEfect;
    }

    public Money getValueMeasureForOne()
    {
        return this.measuere.getDefaultPrice();
    }
}

