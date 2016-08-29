package com.st.ggviario.client.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.st.ggviario.client.model.template.BaseCharacter;

public class Product extends BaseCharacter implements Parcelable
{
	private String id;
	private String name;
	
	public Product(String id, String name)
	{
		this.name = name;
		this.id = id;
	}


	protected Product(Parcel in)
	{
		id = in.readString();
		name = in.readString();
	}

	public static final Creator<Product> CREATOR = new Creator<Product>()
	{
		@Override
		public Product createFromParcel(Parcel in)
		{
			return new Product(in);
		}

		@Override
		public Product[] newArray(int size) {
			return new Product[size];
		}
	};

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int describeContents() {
		return Integer.parseInt(id);
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		Log.i("DBA:APP.TEST", getClass().getSimpleName()+"-> parceleble  i =  "+i);
		parcel.writeString(id);
		parcel.writeString(name);
	}
}
