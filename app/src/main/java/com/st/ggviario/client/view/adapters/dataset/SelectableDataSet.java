package com.st.ggviario.client.view.adapters.dataset;

import android.content.Context;
import android.util.Log;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.util.CharacterIconBuilder;
import com.st.ggviario.client.util.animator.Selectable;

/**
 * Created by Daniel Costa at 9/4/16.
 * Using user computer xdata
 */
public class SelectableDataSet implements BaseRecyclerAdapter.ItemDataSet, Selectable
{
    private final int typeView;
    private CharSequence unSelectedCod;
    private boolean selected;
    private CharSequence selectedCod;
    private int selectedBackground;
    private int unSelectedBackground;
    private Context context;
    private int selectedIconResource;
    private boolean requireBuild;


    public SelectableDataSet(int typeView)
    {
        this.typeView = typeView;
        this.selectedBackground = R.drawable.shap_oval_green;
        this.selectedIconResource = R.drawable.ic_done_white_24dp;
        this.requireBuild = true;
    }

    public SelectableDataSet selected(boolean selected) {
        this.selected = selected;
        return this;
    }

    public SelectableDataSet selectedBackground(int selectedBackground) {
        this.selectedBackground = selectedBackground;
        return this;
    }

    public SelectableDataSet unSelectedBackground(int unSelectedBackground) {
        this.unSelectedBackground = unSelectedBackground;
        return this;
    }

    public SelectableDataSet unSelectedCod(CharSequence unSelectedCod) {
        this.unSelectedCod = unSelectedCod;
        return this;
    }

    public SelectableDataSet selectedCod(CharSequence selectedCod) {
        this.selectedCod = selectedCod;
        return this;
    }

    public SelectableDataSet selectedIconResource(int selectedIconResource) {
        this.selectedIconResource = selectedIconResource;
        return this;
    }

    public SelectableDataSet context(Context context) {
        this.context = context;
        return this;
    }

    public void build()
    {
        if(context != null)
        {
            CharacterIconBuilder characterIconBuilder = new CharacterIconBuilder(context, this.selectedIconResource,"Selected");
            this.selectedCod = characterIconBuilder.getImage();
            this.requireBuild = false;
        }
    }

    public boolean isRequireBuild() {
        return requireBuild;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public CharSequence getSelectedCod() {
        return this.selectedCod;
    }

    @Override
    public int getSelectedBackground() {
        return this.selectedBackground;
    }

    @Override
    public CharSequence getUnSelectedCod() {
        return this.unSelectedCod;
    }

    @Override
    public int getUnSelectedBackground() {
        return this.unSelectedBackground;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int getTypeView() {
        return this.typeView;
    }
}
