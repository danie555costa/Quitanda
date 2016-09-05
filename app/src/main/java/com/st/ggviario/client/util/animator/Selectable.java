package com.st.ggviario.client.util.animator;

/**
 * Created by Daniel Costa at 9/4/16.
 * Using user computer xdata
 */
public interface Selectable {

    boolean isSelected();

    CharSequence getSelectedCod();

    int getSelectedBackground();

    CharSequence getUnSelectedCod();

    int getUnSelectedBackground();

}
