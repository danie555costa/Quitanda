package com.st.ggviario.client.model;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.st.dbutil.android.model.Money;
import com.st.ggviario.client.model.template.BaseCharacter;

import java.io.Serializable;


public class Measure extends BaseCharacter implements Serializable
{
	private final Money priceValueForOne;
	private View viewRoot;
	private ImageButton btGoFinally;
	private TextView tvMetreageName;
	private ImageButton btChwckProduto;
	
	private String idMetreage;
	private String nameMetreage;
	private boolean selected;
	private String codMetreage;
	private String idProducto;
	private TextView valueMetreage;


	public Measure(String idMetreage, String codMetreag, String nameMetreage, double price)
	{
		this.idMetreage = idMetreage;
		this.nameMetreage = nameMetreage;
		this.selected = false;
		this.codMetreage = codMetreag;
		Log.e("APP", "ITEM METREAGE HAS NEW CREATED");
		this.priceValueForOne = new Money(price);
	}

	public void setIdProducto(String idProducto)
	{
		this.idProducto =  idProducto;
	}

	public String getIdMetreage() {
		return idMetreage;
	}

	public String getNameMetreage() {
		return nameMetreage;
	}

	public Money getPriceValueForOne()
	{
		return priceValueForOne;
	}

	private String getIdProducto()
	{
		return this.idProducto;
	}

	public boolean isSelected()
	{
		return this.selected;
	}


	@Override
	public String toString()
	{
		return this.codMetreage;
	}

	public static enum TyteAlter
	{
		CLICK,
		CLICK_BUTTON,
		SETED
	}

	public String getCodMetreage()
	{
		return this.codMetreage;
	}
}
