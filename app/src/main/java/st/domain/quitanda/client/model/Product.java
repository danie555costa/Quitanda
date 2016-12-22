package st.domain.quitanda.client.model;

import st.domain.quitanda.client.util.BaseCharacter;

public class Product extends BaseCharacter
{
	private int id;
	private String name;
	private int stock;
	private Measure baseMesure;

	public Product(int id, String name, Measure baseMeasure)
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Product product = (Product) o;

		return id == product.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
