package st.domain.quitanda.client.model;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class SellRule implements PriceRule
{
    private double quantity;
    private double price;
    private Product product;
    private PriceRule otherRule;
    private double calcAcceptedQuantity;

    public SellRule(double quantity, double price, Product product)
    {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }


    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }

    /**
     * O preco final sera o preco da regra atual somando ao preco devolvido pelo nivel mais a baixo
     * @param quantity A quantidade a ser calculada
     * @return O valor do preco final
     */
    @Override
    public double calc(double quantity)
    {
        double restQuantity = 0;
        double useQuantity  =0;
        double price = 0;

        if(quantity >= this.quantity)
        {
            restQuantity  = quantity % this.quantity;
            useQuantity = quantity - restQuantity;
            price = useQuantity * (this.price/this.quantity);
            this.calcAcceptedQuantity = useQuantity;
        }
        return price + otherRule.calc(restQuantity);
    }

    @Override
    public void setOtherRule(PriceRule otherRule)
    {
        this.otherRule = otherRule;
    }

    public Double getAcceptedQuantity()
    {
        return this.calcAcceptedQuantity + this.otherRule.getAcceptedQuantity();
    }
}
