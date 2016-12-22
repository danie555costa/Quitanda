package st.domain.quitanda.client.model;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.util.BaseCharacter;

/**
 * Created by xdata on 8/9/16.
 */
public class Content extends BaseCharacter implements BaseRecyclerAdapter.ItemDataSet
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
