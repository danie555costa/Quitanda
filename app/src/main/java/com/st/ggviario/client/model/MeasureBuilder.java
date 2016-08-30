package com.st.ggviario.client.model;

import com.st.ggviario.client.references.RData;
import com.st.ggviario.client.references.RMap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class MeasureBuilder implements Builder<Measure>
{
    private double defaultPrice;
    private int id;
    private String name;
    private String key;

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
