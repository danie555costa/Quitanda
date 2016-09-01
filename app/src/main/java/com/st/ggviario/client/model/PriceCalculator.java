package com.st.ggviario.client.model;

import android.util.Log;

import com.st.dbutil.android.process.BackgroundProcess;
import com.st.dbutil.android.process.OnProcessResult;
import com.st.dbutil.android.process.ProcessResult;
import com.st.ggviario.client.dao.DaoProduct;
import com.st.ggviario.client.model.builders.ItemSellBuilder;
import com.st.ggviario.client.model.contract.ObserverCalculated;
import com.st.ggviario.client.model.template.SellRuleFinal;

import java.util.ArrayList;
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
    private ArrayList<ObserverCalculated> listOnCalculed;


    public PriceCalculator(DaoProduct daoProduct)
    {
        this.daoProduct = daoProduct;
    }

    public PriceCalculator idProduct(int idProduct) {
        this.idProduct = idProduct;
        this.listOnCalculed = new ArrayList<>();
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

    public void calc()
    {
        Log.i("DBA:APP.TEST", getClass().getSimpleName()+"-> calc");

        BackgroundProcess backgroundProcess = new BackgroundProcess(this, new OnProcessResult<ItemSell>() {
            @Override
            public void processedResult(ItemSell processResult)
            {
                for(ObserverCalculated item: listOnCalculed)
                    item.accept(processResult);
            }
        });
        backgroundProcess.execute();
    }

    @Override
    public ProcessResult<ItemSell> onExecute(Object... paramns)
    {
        Log.i("DBA:APP.TEST", getClass().getSimpleName()+ "-> onExecute");
        if(idProduct <= 0
                || measureFrom.getId() <= 0
                || quantity == null) return null;

        Product product = this.daoProduct.find(this.idProduct);
        List<PriceRule> rule = this.daoProduct.loadSellRules(product, this.measureFrom);


        //Caso existir as regras de calculo
        if(!rule.isEmpty())
        {
            rule.get(rule.size() -1).setOtherRule(new SellRuleFinal());
            Log.i("DBA:APP.TEST", "Iniciando o calculo");
            PriceRule startRule = rule.get(0);

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

    public PriceCalculator addOnCalculated(ObserverCalculated onCalculated)
    {
        this.listOnCalculed.add(onCalculated);
        return this;
    }
}
