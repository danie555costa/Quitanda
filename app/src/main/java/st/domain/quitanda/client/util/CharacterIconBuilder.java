package st.domain.quitanda.client.util;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;

import st.domain.support.android.adapter.ViewPagerAdpter;

/**
 * Created by Daniel Costa at 9/4/16.
 * Using user computer xdata
 */
public class CharacterIconBuilder
{

    private final Context context;
    private String text;
    private int icon;
    private int bottom;
    private int right;
    private int top;
    private int left;
    private SpannableString image;

    public CharacterIconBuilder(Context context, int icon, String text)
    {
        this.context = context;
        this.icon = icon;
        this.text = text;
        this.defaultSize(24);
        this.buildImage();
    }

    /**
     * Set the default dimension of buildImage in dp
     * <br/>On pos build the default dimension is 24dp
     * <br>On pos set dimesion{
     * <br/>left: 0
     * <br/>top: 0
     * <br/>bottom: dimension in dp
     * <br/>bottom: dimension in dp}
     * @param dimension the dimension in dp
     */
    public void defaultSize(int dimension)
    {
        float dp = this.context.getResources().getDisplayMetrics().density;
        int dimen = (int)(dimension*dp);
        this.bottom = dimen;
        this.right = dimen;
        this.left = 0;
        this.top = 0;
    }

    public CharacterIconBuilder left(int left) {
        this.left = left;
        return this;
    }

    public CharacterIconBuilder top(int top) {
        this.top = top;
        return this;
    }

    public CharacterIconBuilder right(int right) {
        this.right = right;
        return this;
    }

    public CharacterIconBuilder bottom(int bottom) {
        this.bottom = bottom;
        return this;
    }

    public CharacterIconBuilder icon(int icon) {
        this.icon = icon;
        return this;
    }

    public CharacterIconBuilder text(String text) {
        this.text = text;
        return this;
    }

    public CharacterIconBuilder buildImage() {
        Rect bounds = new Rect(this.left, this.top, this.right, this.bottom);
        Drawable icon = this.context.getResources().getDrawable(this.icon);
        icon.setBounds(bounds);
        this.image = ViewPagerAdpter.createSpannableString(this.text, icon, icon.getBounds());
        return this;
    }

    public SpannableString getImage() {
        return image;
    }
}
