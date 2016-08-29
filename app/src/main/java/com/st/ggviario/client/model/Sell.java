package com.st.ggviario.client.model;

import com.st.dbutil.android.model.Money;

import java.io.Serializable;

/**
 * Created by xdata on 7/30/16.
 */
public class Sell implements Serializable
{
    private String id;
    private  String previewID;
    private Client client;
    private Product product;
    private Measure measure;
    private double quantityRequest;
    private double quantityValid;
    private Money valuePayment;
    private  TypeSell type;
    private SellState state;

    public Sell(Client client, Product product, Measure measure, double quantityRequest, double quantityValid, Money montantePayemnt, TypeSell type) {
        this.client = client;
        this.product = product;
        this.measure = measure;
        this.quantityRequest = quantityRequest;
        this.quantityValid = quantityValid;
        this.valuePayment = montantePayemnt;
        this.type = type;
        this.state = SellState.PENDENT;
    }

    public Sell(String id, String previewID, Client client, Product product, Measure measure, double quantityRequest, double quantityValid, Money valuePayment, TypeSell type, SellState state) {
        this.id = id;
        this.previewID = previewID;
        this.client = client;
        this.product = product;
        this.measure = measure;
        this.quantityRequest = quantityRequest;
        this.quantityValid = quantityValid;
        this.valuePayment = valuePayment;
        this.type = type;
        this.state = state;
    }


    public String getId() {
        return id;
    }

    public String getPreviewID() {
        return previewID;
    }

    public Client getClient() {
        return client;
    }

    public Product getProduct() {
        return product;
    }

    public Measure getMeasure() {
        return measure;
    }

    public double getQuantityRequest() {
        return quantityRequest;
    }

    public double getQuantityValid() {
        return quantityValid;
    }

    public Money getValuePayment() {
        return valuePayment;
    }

    public TypeSell getType() {
        return type;
    }

    public SellState getState() {
        return state;
    }

    public enum TypeSell implements Serializable
    {
        SELL(1),
        CREDIT(2);

        private final int CORRESPONDENCE_DATABASE;

        TypeSell(int correspondence)
        {
            this.CORRESPONDENCE_DATABASE = correspondence;
        }

        public int getCORRESPONDENCE_DATABASE()
        {
            return CORRESPONDENCE_DATABASE;
        }

        public static TypeSell TypeOf(int correspondentDataBase)
        {
            for(TypeSell type: values())
                if(type.CORRESPONDENCE_DATABASE == correspondentDataBase)
                    return type;
            return null;
        }
    }


    public enum SellState
    {
        PENDENT(-1),
        REGISTED(1),
        SYNCRONIZING(2),
        SYNCRONIZED(3);

        private final int CORRESPONDENCE_DATABASE;
        SellState(int correspondence)
        {
            this.CORRESPONDENCE_DATABASE= correspondence;
        }

        public int getCORRESPONDENCE_DATABASE() {
            return CORRESPONDENCE_DATABASE;
        }
    }
}
