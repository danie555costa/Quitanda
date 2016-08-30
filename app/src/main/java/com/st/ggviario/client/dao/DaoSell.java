package com.st.ggviario.client.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.st.dbutil.android.model.Date;
import com.st.dbutil.android.model.Money;
import com.st.dbutil.android.sqlite.LiteDataBase;
import com.st.ggviario.client.model.Client;
import com.st.ggviario.client.model.Measure;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.model.ProductBuilder;
import com.st.ggviario.client.model.Sell;
import com.st.ggviario.client.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.st.ggviario.client.references.RData.ALL;
import static com.st.ggviario.client.references.RData.DATABASE_NAME;
import static com.st.ggviario.client.references.RData.DATABASE_VERSION;
import static com.st.ggviario.client.references.RData.REQ_BASEQUANTITY;
import static com.st.ggviario.client.references.RData.REQ_CLI_ID;
import static com.st.ggviario.client.references.RData.REQ_DTENTREGA;
import static com.st.ggviario.client.references.RData.REQ_DTPAGAR;
import static com.st.ggviario.client.references.RData.REQ_MET_ID;
import static com.st.ggviario.client.references.RData.REQ_MONTPAYMENT;
import static com.st.ggviario.client.references.RData.REQ_PREVIEWID;
import static com.st.ggviario.client.references.RData.REQ_PROD_ID;
import static com.st.ggviario.client.references.RData.REQ_QUANTITY;
import static com.st.ggviario.client.references.RData.REQ_TREQ_ID;
import static com.st.ggviario.client.references.RData.REQ_USER_ID;
import static com.st.ggviario.client.references.RData.SELL_PRICE;
import static com.st.ggviario.client.references.RData.T_REQUEST;
import static com.st.ggviario.client.references.RData.VER_OPERATION_DAY;

/**
 * Created by xdata on 7/30/16.
 */
public class DaoSell extends LiteDataBase
{
    public DaoSell(Context context)
    {
        super(context, DATABASE_NAME, DATABASE_VERSION);
        context = super.getContext();
    }

    /**
     * Registera uma nova operacao de venda e de requisicao
     * @param client
     * @param product
     * @param measure
     * @param requerideQuantity
     * @param validQuantity
     * @param valuePayment
     * @param type
     * @param dateEntrega
     * @param datePaymnert
     * @return
     */
    public boolean regSell(Client client, Product product, Measure measure, double requerideQuantity, double validQuantity, Money valuePayment, Sell.TypeSell type, Date dateEntrega, Date datePaymnert)
    {
        String idCliente = client.getId();
        User user = DaoUser.geUser(this.getContext());
        if(client == null)
            client = DaoClient.getAnonimeCLient();
        if(user == null)
        {
            Log.e("DBA:APP.TEST", "Situação inesperada o utilizador nao foi encontrado");
            throw new Error("Situação inesperada o utilizador nao foi encontrado");
        }

        int idProduct = product.getId();
        int idMetreage = measure.getId();
        int idUser = user.getId();
        begin(Operaction.INSERT);

        insertInto(T_REQUEST, REQ_PREVIEWID)
                .columns(REQ_CLI_ID, REQ_PROD_ID, REQ_MET_ID, REQ_USER_ID, REQ_QUANTITY, REQ_BASEQUANTITY, REQ_MONTPAYMENT, REQ_TREQ_ID, REQ_DTENTREGA, REQ_DTPAGAR)
                .values(idCliente, idProduct, idMetreage, idUser, requerideQuantity, validQuantity, valuePayment, type.getCORRESPONDENCE_DATABASE(), dateEntrega, datePaymnert)
                .returning(null)
                .execute();
        Object result = getResult();
        LinkedHashMap<CharSequence, Object> map = getInserttResult();
        end();
        if((long) result != -1) return true;
        else return false;
    }


    public ArrayList<Sell> loadOperactionsDay(final Sell.TypeSell type)
    {
        begin(Operaction.SELECT);
        select(ALL)
                .from(VER_OPERATION_DAY)
                .where(new Condicion() {
                    @Override
                    public boolean accept(int wherePosition, HashMap<CharSequence, Object> row) {
                        return  type == null
                                || type.getCORRESPONDENCE_DATABASE() == ((int)row.get(REQ_TREQ_ID));
                    }
                })
                .execute();

        ArrayList<LinkedHashMap<CharSequence, Object>> result = this.getSelectResult();
        end();

        ArrayList<Sell> list = new ArrayList<>();
        for(LinkedHashMap<CharSequence, Object> map: result)
        {
            Sell sell = mountSellOperation(map);
            list.add(sell);
        }
        return  list;
    }

    @NonNull
    public static Sell mountSellOperation(LinkedHashMap<CharSequence, Object> map)
    {
        Client client = DaoClient.mountClient(map);
        map.put(SELL_PRICE, map.get(REQ_MONTPAYMENT));
        Product product = new ProductBuilder().build(map);
        Measure measure = DaoProduct.mountMeasure(map);

        double quantityReq = (double) map.get(REQ_QUANTITY);

        double quantityValid =  Double.parseDouble(map.get(REQ_BASEQUANTITY).toString());
        Money value = new Money((double) map.get(REQ_MONTPAYMENT));
        Sell.TypeSell type = Sell.TypeSell.TypeOf((int) map.get(REQ_TREQ_ID));
        return new Sell(client, product, measure, quantityReq, quantityValid, value, type);
    }


//    public SellHome.SummarySell creatSummary()
//    {
//        begin(Operaction.SELECT);
//        select(ALL)
//                .from(VER_SUMMARY_SELL)
//                .execute();
//        ArrayList<LinkedHashMap<CharSequence, Object>> result = getSelectResult();
//        end();
//
//        long total =  Long.parseLong(result.get(0).get(SUMSELL_TOTAL).toString());
//        String valueTotal = (result.get(0).get(SUMSELL_TOTAL_VALUE) == null)? "0.0" : result.get(0).get(SUMSELL_TOTAL_VALUE).toString();
//        double totalValue = Double.parseDouble(valueTotal);
//
//        long sellTotal = Long.parseLong(result.get(0).get(SUMSELL_SELL).toString());
//        String valueSell = (result.get(0).get(SUMSELL_SELL_VALUE) == null)? "0.0" : result.get(0).get(SUMSELL_SELL_VALUE).toString();
//        double sellValue = Double.parseDouble(valueSell);
//
//        long creditTotal = Long.parseLong(result.get(0).get(SUMSELL_CREDIT).toString());
//        String valueCredit = (result.get(0).get(SUMSELL_CREDIT_VALUE) == null)? "0.0" : result.get(0).get(SUMSELL_CREDIT_VALUE).toString();
//        double creditValue = Double.parseDouble(valueCredit);
//
//        SellHome.SummarySell summary = new SellHome.SummarySell(total, totalValue, sellTotal, sellValue, creditTotal, creditValue);
//        return summary;
//    }
}
