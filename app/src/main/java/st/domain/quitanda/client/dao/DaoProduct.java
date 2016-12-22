package st.domain.quitanda.client.dao;

import android.content.Context;

import st.domain.support.android.model.OnProcess;
import st.domain.support.android.sqlite.LiteDataBase;
import st.domain.quitanda.client.model.Measure;
import st.domain.quitanda.client.model.PriceRule;
import st.domain.quitanda.client.model.Product;
import st.domain.quitanda.client.model.builders.ProductBuilder;
import st.domain.quitanda.client.model.SellRule;
import st.domain.quitanda.client.references.RData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by xdata on 7/24/16.
 */
public class DaoProduct extends LiteDataBase
{
    public DaoProduct(Context context)
    {
        super(context, RData.DATABASE_NAME, RData.DATABASE_VERSION);
    }

    public Double convertMeasures(final int idMetrageFromm, final int idMetreaTo, Double valuemetreage1)
    {
        Double conversionResult, conversionFrom, conversionTo;
        ArrayList<LinkedHashMap<CharSequence, Object>> result;

        //-- Se for para converter os mesmos tipo de medida entao a regra sera
        //-- retornar o mesmo valor a converter
        if((idMetrageFromm+"").equals(idMetreaTo+"")) return valuemetreage1;

//        -- Quando as medidas forem difrentes entao calcular o valor para a medida
//        -- esperada a partir da conversao definidada da mesma
//        -- Carregar a conversao
        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.T_CONVERSION)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row) {
                        String conv_met_id1 = row.get(RData.CONV_MET_ID1)+"";
                        String conv_met_id2 =row.get(RData.CONV_MET_ID2)+"";
                        return ((conv_met_id1.equals(idMetrageFromm+"")
                                && conv_met_id2.equals(idMetreaTo+""))
                                || (conv_met_id2.equals(idMetrageFromm+"")
                                    && conv_met_id1.equals(idMetreaTo+"")));
                    }
                });
        execute();
        result = getSelectResult();
        end();

//        -- Caso nao encontre nenhuma regra de converter essas duas medidas entao retornar o valor de null
        if(result.size()== 0) return null;
        if((result.get(0).get(RData.CONV_MET_ID1)).toString().equals(idMetrageFromm+""))
        {
            conversionFrom = ((Number)result.get(0).get(RData.CONV_VALUE1)).doubleValue();
            conversionTo = ((Number)result.get(0).get(RData.CONV_VALUE2)).doubleValue();
        }
        else
        {
            conversionFrom = ((Number)result.get(0).get(RData.CONV_VALUE2)).doubleValue();
            conversionTo = ((Number)result.get(0).get(RData.CONV_VALUE1)).doubleValue();
        }
        conversionResult = (valuemetreage1 * conversionTo)/conversionFrom;
        return conversionResult;
    }

    public Product find(final int idProducto)
    {
        LinkedHashMap<CharSequence, Object> map;
        Product produto ;
        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.VER_PRODUCT_COMPLETE)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row) {
                        return Objects.equals(row.get(RData.PROD_ID), idProducto);
                    }
                });
        execute();
        map = getSelectResult().get(0);
        end();

        return new ProductBuilder().buildMap(map);
    }

    public ArrayList<Measure> loadMetreages(final int idProducto)
    {
        ArrayList<Measure> metreages = new ArrayList<>();
        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.VER_METREAGE_PRODUCT)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row) {
                        return Objects.equals(row.get(RData.PROD_ID), idProducto);
                    }
                });
        execute();

        Measure itemMetreage;

        for(LinkedHashMap<CharSequence, Object> map : getSelectResult())
        {
            itemMetreage = mountMeasure(map);
//            itemMetreage.setIdProducto(idProducto);
            metreages.add(itemMetreage);
        }
        end();
        return metreages;
    }

    public static Measure mountMeasure(LinkedHashMap<CharSequence, Object> map)
    {
        int idMetreage = (int) map.get(RData.MET_ID);
        String metreageName = map.get(RData.MET_NAME).toString();
        String codMetreage = map.get(RData.MET_COD).toString();
        Double precoDefault = (Double) map.get(RData.SELL_PRICE);
        if(precoDefault == null) precoDefault = 0.0;

        Measure itemMetreage = new Measure(idMetreage, codMetreage, metreageName, precoDefault);
        return itemMetreage;
    }

    public ArrayList<Product> loadProducts(OnProcess<Product> productOnProcess)
    {
        ArrayList<Product> listProdutoShell = new ArrayList<>();
        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.VER_PRODUTO_SELL);
        execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();
        end();
        for(LinkedHashMap<CharSequence, Object> map: result)
        {
            Product product = new ProductBuilder().buildMap(map);
            if(productOnProcess != null)
                productOnProcess.process(product);
            listProdutoShell.add(product);
        }
        return listProdutoShell;
    }

    public List<PriceRule> loadSellRules(final Product product, final Measure idMeasureFrom)
    {
        List<PriceRule> list = new ArrayList<>();
        begin(Operaction.SELECT);
        select(RData.ALL)
                .from(RData.VER_SELLROULE)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row)
                    {
                        boolean result = Objects.equals(row.get(RData.SELL_PROD_ID), product.getId())
                                && Objects.equals(row.get(RData.SELL_MET_ID), idMeasureFrom.getId());

                        return result;
                        //ORDER BY sh.sell_quantity DESC Executed by view
                    }});
        execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();

        SellRule last = null;
        SellRule current;

        for(LinkedHashMap<CharSequence, Object>map: result)
        {
            current =(new SellRule((Double) map.get(RData.SELL_QUANTITY), (Double) map.get(RData.SELL_PRICE), product));
            if(last != null)
                last.setOtherRule(current);
            last = current;
            list.add(current);
        }
        return list;
    }
}
