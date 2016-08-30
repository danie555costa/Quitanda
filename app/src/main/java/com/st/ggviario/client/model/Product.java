package com.st.ggviario.client.model;

import com.st.ggviario.client.model.template.BaseCharacter;

public class Product extends BaseCharacter
{
	private int id;
	private String name;
	private int stock;
	private Measure baseMesure;

	Product(int id, String name, Measure baseMeasure)
	{
		this.name = name;
		this.id = id;
		this.baseMesure = baseMeasure;
	}

	public int getId() {
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

	public Measure getBaseMesure()
	{
		return baseMesure;
	}
}
