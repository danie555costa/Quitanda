package com.st.ggviario.client.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.st.ggviario.client.model.Measure;
import com.st.ggviario.client.model.MeasureBuilder;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class MeasureParcel implements Parcelable
{
    private Measure measure;

    public MeasureParcel(Measure measure) {
        this.measure = measure;
    }

    protected MeasureParcel(Parcel in)
    {
        MeasureBuilder builder = new MeasureBuilder();
        builder.id(in.readInt())
                .key(in.readString())
                .name(in.readString())
                .defaultPrice(in.readDouble());

        this.measure = builder.build();
    }

    public Measure getMeasure() {
        return measure;
    }

    public static final Creator<MeasureParcel> CREATOR = new Creator<MeasureParcel>() {
        @Override
        public MeasureParcel createFromParcel(Parcel in) {
            return new MeasureParcel(in);
        }

        @Override
        public MeasureParcel[] newArray(int size) {
            return new MeasureParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(measure.getId());
        out.writeString(measure.getKey());
        out.writeString(measure.getName());
        out.writeDouble(measure.getDefaultPrice().doubleValue());
    }
}
