package com.st.ggviario.client.model.parcelable;


import android.os.Parcel;
import android.os.Parcelable;

import com.st.ggviario.client.model.ProductBuilder;
import com.st.ggviario.client.model.Product;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class ProductParcel implements Parcelable
{
    Product product;

    public ProductParcel(Product product)
    {
        this.product = product;
    }

    protected ProductParcel(Parcel in)
    {
        ProductBuilder builder = new ProductBuilder();
        builder.id(in.readString())
                .name(in.readString());

        this.product = builder.build();
    }

    /**
     * Get the product mouted
     * @return the product instace
     */
    public Product getProduct() {
        return product;
    }

    public static final Creator<ProductParcel> CREATOR = new Creator<ProductParcel>()
    {
        @Override
        public ProductParcel createFromParcel(Parcel in) {
            return new ProductParcel(in);
        }

        @Override
        public ProductParcel[] newArray(int size) {
            return new ProductParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(product.getId());
        parcel.writeString(product.getName());
    }
}
