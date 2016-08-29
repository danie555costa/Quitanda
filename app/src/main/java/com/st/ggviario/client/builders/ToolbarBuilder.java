package com.st.ggviario.client.builders;

import android.support.v7.widget.Toolbar;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class ToolbarBuilder implements Builder<Toolbar>
{
    private Toolbar toolbar;

    public ToolbarBuilder (Toolbar toolbar)
    {
        this.toolbar = toolbar;
    }

    public ToolbarBuilder title(CharSequence title)
    {
        toolbar.setTitle(title);
        return this;
    }

    @Override
    public Toolbar build() {
        return this.toolbar;
    }

    public ToolbarBuilder title(int title) {
        this.toolbar.setTitle(title);
        return this;
    }
}
