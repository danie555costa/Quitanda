package com.st.ggviario.client.model;

import android.util.Log;

import com.st.dbutil.android.process.BackgroundProcess;
import com.st.dbutil.android.process.OnProcessResult;
import com.st.dbutil.android.process.ProcessResult;
import com.st.ggviario.client.dao.DaoProduct;
import com.st.ggviario.client.model.template.SellRuleFinal;

import java.util.List;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class PriceCalculator implements BackgroundProcess.Background<ProcessResult> {
    private final DaoProduct daoProduct;

    private int idProduct;
    private Double quantity;
    private Measure measureFrom;


    public PriceCalculator(DaoProduct daoProduct)
    {
        this.daoProduct = daoProduct;
    }

    public PriceCalculator idProduct(int idProduct) {
        this.idProduct = idProduct;
        return this;
    }

    public PriceCalculator quantity(double quantity) {
        this.quantity = quantity;
        return this;
    }

    public PriceCalculator idMetreageFrom(Measure measureFrom)
    {
        this.measureFrom = measureFrom;
        return this;
    }

    public void calc(OnProcessResult<ItemSell> onResultCalc)
    {
        BackgroundProcess backgroundProcess = new BackgroundProcess(this, onResultCalc);
        backgroundProcess.execute();
    }

    @Override
    public ProcessResult onExecute(Object... paramns)
    {
        if(idProduct <= 0
                || measureFrom.getId() <= 0
                || quantity == null) return null;

        Product product = this.daoProduct.find(this.idProduct);
        List<SellRule> roules = this.daoProduct.loadSellRules(product, this.measureFrom);

        //Caso existir as regras de calculo
        if(!roules.isEmpty())
        {
            roules.get(roules.size() -1).setOtherRule(new SellRuleFinal());
            Log.i("DBA:APP.TEST", "Iniciando o calculo");
            SellRule startRule = roules.get(0);
            double amountValue = startRule.calc(this.quantity);
            Double usedQuantity = startRule.getAcceptedQuantity();
            Double baseQuantity = daoProduct.convertMeasures(this.measureFrom.getId(), product.getBaseMesure().getId(), usedQuantity);

            ItemSellBuilder builder = new ItemSellBuilder();
            ItemSell build = builder.amountPay(amountValue)
                    .baseQuantity(baseQuantity)
                    .usedQuantity(usedQuantity)
                    .product(product)
                    .requestQuantity(this.quantity)
                    .measure(this.measureFrom)
                    .build();
            Log.i("DBA:APP.TEST", "Calculo terminado Resuylt "+ build);
            return  build;
        }
        return null;
    }
}
