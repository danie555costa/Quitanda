package com.st.ggviario.client.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.st.ggviario.client.model.ItemSellBuilder;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class ItemSellParcel implements Parcelable
{
    protected ItemSellParcel(Parcel in)
    {
        ItemSellBuilder builder = new ItemSellBuilder();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemSellParcel> CREATOR = new Creator<ItemSellParcel>() {
        @Override
        public ItemSellParcel createFromParcel(Parcel in) {
            return new ItemSellParcel(in);
        }

        @Override
        public ItemSellParcel[] newArray(int size) {
            return new ItemSellParcel[size];
        }
    };
}
