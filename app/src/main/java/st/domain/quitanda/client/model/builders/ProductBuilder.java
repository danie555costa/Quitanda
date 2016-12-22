package st.domain.quitanda.client.model.builders;

import st.domain.quitanda.client.model.Measure;
import st.domain.quitanda.client.model.Product;

import java.util.LinkedHashMap;

import static st.domain.quitanda.client.references.RData.PROD_ID;
import static st.domain.quitanda.client.references.RData.PROD_NAME;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class ProductBuilder extends Builder<Product>
{
    private String name;
    private int id;
    private CharSequence measure;


    public ProductBuilder() {
        super(Product.class);
    }

    public ProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder id(int id) {
        this.id = id;
        return this;
    }

    public ProductBuilder measure(CharSequence measure) {
        this.measure = measure;
        return this;
    }

    public Product build() {
        Measure measure;
        if(this.measure instanceof Measure)
            measure = (Measure) this.measure;
        else
        {
            MeasureBuilder measureBuilder = new MeasureBuilder();
            measure = measureBuilder.id(Integer.parseInt(this.measure.toString()))
                    .build();

        }
        return new Product(id, name, measure);
    }

    public Product buildMap(LinkedHashMap<CharSequence, Object> map)
    {
        return id((int) map.get(PROD_ID))
                .name(map.get(PROD_NAME).toString())
                .measure(new MeasureBuilder().buildMap(map))
                .build();
    }

}
