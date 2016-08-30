package com.st.ggviario.client.model;

import com.st.ggviario.client.model.template.BaseCharacter;

public class Product extends BaseCharacter
{
	private String id;
	private String name;
	private int stock;
	
	Product(String id, String name)
	{
		this.name = name;
		this.id = id;
	}

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
}
