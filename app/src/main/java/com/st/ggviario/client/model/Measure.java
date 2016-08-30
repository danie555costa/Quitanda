package com.st.ggviario.client.model;

import com.st.dbutil.android.model.Money;
import com.st.ggviario.client.model.template.BaseCharacter;

import java.io.Serializable;


public class Measure extends BaseCharacter implements Serializable
{
	private final Money defaultPrice;
	private int id;
	private String name;
	private String key;


	public Measure(int id, String key, String name, double price)
	{
		this.id = id;
		this.name = name;
		this.key = key;
		this.defaultPrice = new Money(price);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Money getDefaultPrice()
	{
		return defaultPrice;
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
}
