package st.domain.quitanda.client.model;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public interface PriceRule
{
    double calc(double quantity);

    void setOtherRule(PriceRule nextRule);

    Double getAcceptedQuantity();
}
