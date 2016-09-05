package com.st.ggviario.client.model;

import com.st.dbutil.android.model.Money;
import com.st.ggviario.client.util.BaseCharacter;

import java.io.Serializable;


public class Measure extends BaseCharacter implements Serializable
{
	private double defaultPrice;
	private int id;
	private String name;
	private String key;

	public Measure(int id, String key, String name, double price)
	{
		this.id = id;
		this.name = name;
		this.key = key;
		this.defaultPrice = (price);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Money getDefaultPrice()
	{
		return new Money(defaultPrice);
	}

	@Override
	public String toString()
	{
		return this.key;
	}

	public String getKey()
	{
		return this.key;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Measure measure = (Measure) o;

		return id == measure.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
