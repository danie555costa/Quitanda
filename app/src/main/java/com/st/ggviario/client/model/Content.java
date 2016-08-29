package com.st.ggviario.client.model;

import com.st.dbutil.android.adapter.BaseReclyclerAdapter;
import com.st.ggviario.client.model.template.BaseCharacter;

/**
 * Created by xdata on 8/9/16.
 */
public class Content extends BaseCharacter implements BaseReclyclerAdapter.ItemDataSet
{
    private String title;
    private String value;
    private int imageResource;

    public Content(String title, String value, int img_resource) {
        this.title = title;
        this.value = value;
        this.imageResource = img_resource;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public int getImageResource() {
        return imageResource;
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int getTypeView() {
        return 0;
    }
}
