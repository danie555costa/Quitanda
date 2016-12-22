package st.domain.quitanda.client.model.builders;

import st.domain.quitanda.client.model.Measure;
import st.domain.quitanda.client.references.RData;

import java.util.LinkedHashMap;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class MeasureBuilder extends Builder<Measure>
{
    private double defaultPrice;
    private int id;
    private String name;
    private String key;

    public MeasureBuilder() {
        super(Measure.class);
    }

    public MeasureBuilder defaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
        return this;
    }

    public MeasureBuilder id(int id) {
        this.id = id;
        return this;
    }

    public MeasureBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MeasureBuilder key(String key) {
        this.key = key;
        return this;
    }

    @Override
    public Measure build() {
        return new Measure(id, key, name, defaultPrice);
    }

    public Measure buildMap(LinkedHashMap<CharSequence, Object> map)
    {
        return id((int) map.get(RData.MET_ID))
                .key(map.get(RData.MET_COD).toString())
                .name(map.get(RData.MET_NAME).toString())
                .build();
    }
}
