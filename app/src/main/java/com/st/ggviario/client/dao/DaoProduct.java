package com.st.ggviario.client.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.st.dbutil.android.model.OnProcess;
import com.st.dbutil.android.process.BackgroundProcess;
import com.st.dbutil.android.process.OnProcessResult;
import com.st.dbutil.android.process.ProcessResult;
import com.st.dbutil.android.sqlite.LiteDataBase;
import com.st.ggviario.client.model.ProductBuilder;
import com.st.ggviario.client.model.Measure;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.model.ResultPrice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.st.dbutil.android.sqlite.LiteDataBase.Operaction.SELECT;
import static com.st.ggviario.client.references.RData.*;

/**
 * Created by xdata on 7/24/16.
 */
public class DaoProduct extends LiteDataBase implements BackgroundProcess.Background<ResultPrice>  // implements BackgroundProcess.Background<DaoProduct.ResultPrice>
{
    public DaoProduct(Context context)
    {
        super(context, DATABASE_NAME, DATABASE_VERSION);
    }

    private Double func_convert_metreage(final String idMetrageFromm, final int idMetreaTo, Double valuemetreage1)
    {
        Double conversionResult, conversionFrom, conversionTo;
        ArrayList<LinkedHashMap<CharSequence, Object>> result;

        //-- Se for para converter os mesmos tipo de medida entao a regra sera
        //-- retornar o mesmo valor a converter
        if((idMetrageFromm+"").equals(idMetreaTo+"")) return valuemetreage1;

//        -- Quando as medidas forem difrentes entao calcular o valor para a medida
//        -- esperada a partir da conversao definidada da mesma
//        -- Carregar a conversao
        begin(SELECT);
        select(ALL)
                .from(T_CONVERSION)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row) {
                        String conv_met_id1 = row.get(CONV_MET_ID1)+"";
                        String conv_met_id2 =row.get(CONV_MET_ID2)+"";
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
        if((result.get(0).get(CONV_MET_ID1)).toString().equals(idMetrageFromm+""))
        {
            conversionFrom = ((Number)result.get(0).get(CONV_VALUE1)).doubleValue();
            conversionTo = ((Number)result.get(0).get(CONV_VALUE2)).doubleValue();
        }
        else
        {
            conversionFrom = ((Number)result.get(0).get(CONV_VALUE2)).doubleValue();
            conversionTo = ((Number)result.get(0).get(CONV_VALUE1)).doubleValue();
        }
        conversionResult = (valuemetreage1 * conversionTo)/conversionFrom;
        return conversionResult;
    }

    /**
     * Calcular o preco do produto
     * @param idProductS
     * @param quantidade
     * @param idMetrageFromm
     * @param onResultCalc
     */
    public void calcPrice(final String idProductS, double quantidade, final String idMetrageFromm, OnProcessResult<ResultPrice> onResultCalc)
    {
        BackgroundProcess resultPriceBackgroud = new BackgroundProcess(this, onResultCalc);
        resultPriceBackgroud.execute(idProductS, quantidade, idMetrageFromm);
    }

    @Override
    public ResultPrice onExecute(Object... paramns)
    {
        super.setDebugable(false, -36789);
        final String idProduct = (String) paramns[0];
        double quantity = (double) paramns[1];
        final String idMeasureFrom = (String) paramns[2];

        double quantityRequiredRestante = quantity;
        boolean hasRoule = false;
        Double rowRoule=null, definedQuantity, definedPrice, baseQuantity, quantidadeRedonda;
        Double restoSobrado, baseQuantityTotal = 0.0;
        double valueFinallyPay = 0, quantityTotalUsed =0 , rowQuantity = 0;
        ArrayList<LinkedHashMap<CharSequence, Object>> result;

        int idMeasureTo;
        begin(SELECT);
        select(ALL)
                .from(T_PRODUCT)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row) {return (row.get(PROD_ID)+"").equals(idProduct);}
                });
        execute();
        result = getSelectResult();
        end();

        idMeasureTo = ((Number)result.get(0).get(PROD_MET_ID)).intValue();
        result.clear();

        //Enquanto for possivel retira mais quantidadeRequerida entao..end();

        while (quantityRequiredRestante> 0.0)
        {

            //A primeira cois é ter a lista as regas dos precos
            //-- pegar o preco e a quantidade mais adequada
            begin(SELECT);
            final double newQuantityRequired = quantityRequiredRestante;
            select(ALL)
                    .from(VER_SELLROULE)
                    .where(new Condicion() {
                        @Override
                        public boolean accept(int wherePosition, HashMap<CharSequence, Object> row)
                        {

                            Log.i("DBA:APP.TEST", "REGRA ENCONTRADA | NOVA QUANTIDADE =  "+newQuantityRequired+", QUNATIDADE REGRA = "+((Number)row.get(SELL_QUANTITY)).doubleValue());

                            return (row.get(SELL_PROD_ID)+"").equals(idProduct)
                                    && (row.get(SELL_MET_ID)+"").equals(idMeasureFrom)
                                    && ((Number)row.get(SELL_QUANTITY)).doubleValue()<= newQuantityRequired;
                            //ORDER BY sh.sell_quantity DESC Executed by view
                        }})
                    .limit(1);
            execute();
            result = getSelectResult();
            end();
            Log.i("DBA:APP.TEST", getClass().getSimpleName()+"-> "+result);

            if(result.size()>0)
            {
                hasRoule = true;
                definedQuantity = ((Number) result.get(0).get(SELL_QUANTITY)).doubleValue();
                definedPrice = ((Number) result.get(0).get(SELL_PRICE)).doubleValue();

                // -- O utilimo preco usado
                if(rowRoule == null)
                {
                    rowRoule = definedQuantity;
                }

                restoSobrado = quantityRequiredRestante % definedQuantity;
                restoSobrado = quantityRequiredRestante - restoSobrado;

                if (quantityRequiredRestante - restoSobrado > 0.0 && restoSobrado >0.0)
                {
                    rowQuantity = rowQuantity + quantityRequiredRestante;
                    valueFinallyPay = valueFinallyPay + ((definedPrice/definedQuantity) * restoSobrado);  // -- VALOR TOTAL
                    quantityRequiredRestante = quantityRequiredRestante - restoSobrado; //-- QUANTIDADE RESTANTE
                    quantityTotalUsed = quantityTotalUsed + restoSobrado; //-- QUANTIDADE TOTAL USADA
                }
                else
                {
                    restoSobrado = quantityRequiredRestante;
                    rowQuantity = rowQuantity + quantityRequiredRestante;
                    valueFinallyPay = valueFinallyPay + ((definedPrice/definedQuantity) * restoSobrado); // -- VALOR TOTAL
                    quantityTotalUsed = quantityTotalUsed + restoSobrado; // -- QUANTIDADE TOTAL USADA
                    quantityRequiredRestante = 0;
                }

                if(rowRoule != definedQuantity)
                {
                    baseQuantity = this.func_convert_metreage(idMeasureFrom, idMeasureTo, rowQuantity);
                    baseQuantityTotal = baseQuantityTotal + baseQuantity;
                    //Quandtidade base da regra sera desfeita para um nova regra
                    rowQuantity = 0.0;
                }

                rowRoule = definedQuantity;
            }
            else
            {
                quantityRequiredRestante = 0.0;
            }
        }//END LOOP

        if(hasRoule)
        {
            baseQuantity = func_convert_metreage(idMeasureFrom, idMeasureTo, rowQuantity);
            if(baseQuantity != null)
                baseQuantityTotal = baseQuantityTotal + baseQuantity;
            else baseQuantityTotal = null;
            ResultPrice resultPreco = new ResultPrice(quantityTotalUsed, baseQuantityTotal, valueFinallyPay);
            return resultPreco;
        }
        return  null;
    }

    @Override
    public void accept(ResultPrice result, OnProcessResult onProcessResult)
    {
        onProcessResult.processResult(result);
    }

    public Product find(final String idProducto)
    {
        LinkedHashMap<CharSequence, Object> map;
        Product produto ;
        begin(SELECT);
        select(ALL)
                .from(T_PRODUCT)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row) {
                        return row.get(PROD_ID).toString().equals(idProducto);
                    }
                });
        execute();
        map = getSelectResult().get(0);
        end();

        produto = mountProduct(map);
        return  produto;
    }

    @NonNull
    public static Product mountProduct(LinkedHashMap<CharSequence, Object> produtoData)
    {
        String id = produtoData.get(PROD_ID).toString();
        String name = produtoData.get(PROD_NAME).toString();
        ProductBuilder builder = new ProductBuilder();

        return builder.id(id)
                .name(name)
                .build();
    }

    public ArrayList<Measure> loadMetreages(final String idProducto)
    {
        ArrayList<Measure> metreages = new ArrayList<>();
        begin(SELECT);
        select(ALL)
                .from(VER_METREAGE_PRODUCT)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row) {
                        return row.get(PROD_ID).toString().equals(idProducto);
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
        String idMetreage = map.get(MET_ID).toString();
        String metreageName = map.get(MET_NAME).toString();
        String codMetreage = map.get(MET_COD).toString();
        Double precoDefault = (Double) map.get(SELL_PRICE);
        if(precoDefault == null) precoDefault = 0.0;

        Measure itemMetreage = new Measure(idMetreage, codMetreage, metreageName, precoDefault);
        return itemMetreage;
    }

    public ArrayList<Product> loadProducts(OnProcess<Product> productOnProcess)
    {
        ArrayList<Product> listProdutoShell = new ArrayList<>();
        begin(SELECT);
        select(ALL)
                .from(VER_PRODUTO_SELL);
        execute();
        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();
        end();
        for(LinkedHashMap<CharSequence, Object> map: result)
        {
            Product product = mountProduct(map);
            if(productOnProcess != null)
                productOnProcess.process(product);
            listProdutoShell.add(product);
        }
        return listProdutoShell;
    }
}
