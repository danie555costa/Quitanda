package com.st.ggviario.client.model;

import com.st.dbutil.android.process.ProcessResult;

import java.io.Serializable;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class ResultPrice implements ProcessResult
{
    public final double valueFinalPagar;
    public final Double quantityBaseTotal;
    public final double quantidadeTotalUsada;

    public ResultPrice(double quantidadeTotalUsada, Double quantityBaseTotal, double valueFinalPagar)
    {
        this.quantidadeTotalUsada = quantidadeTotalUsada;
        this.quantityBaseTotal = quantityBaseTotal;
        this.valueFinalPagar = valueFinalPagar;
    }

    public double getValueFinalPagar() {
        return valueFinalPagar;
    }

    public Double getQuantityBaseTotal() {
        return quantityBaseTotal;
    }

    public double getQuantidadeTotalUsada() {
        return quantidadeTotalUsada;
    }

    @Override
    public String toString() {
        return "ResultPrice{" +
                "valueFinalPagar=" + valueFinalPagar +
                ", quantityBaseTotal=" + quantityBaseTotal +
                ", quantidadeTotalUsada=" + quantidadeTotalUsada +
                '}';
    }
}
