package com.st.ggviario.client.view.activitys;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.github.fcannizzaro.materialstepper.style.TabStepper;
import com.st.dbutil.android.beans.CallbackControler;
import com.st.dbutil.android.model.CallbackClient;
import com.st.dbutil.android.model.Money;
import com.st.dbutil.android.model.OnProcess;
import com.st.dbutil.android.sqlite.DMLite;
import com.st.dbutil.android.view.SlidingTabLayout;
import com.st.ggviario.client.model.ItemSell;
import com.st.ggviario.client.references.RMap;
import com.st.ggviario.client.dao.DaoProduct;
import com.st.ggviario.client.model.Client;
import com.st.ggviario.client.model.Measure;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.view.adapters.dataset.MeasureDataSet;
import com.st.ggviario.client.view.fragments.SellCarStep;
import com.st.ggviario.client.view.fragments.SellClientFragment;
import com.st.ggviario.client.view.fragments.SellClientStep;
import com.st.ggviario.client.view.fragments.SellPayment;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

public class SellStepperActivity extends TabStepper implements RMap, CallbackClient
{
    public static final String QUANTITY = "QUANTITY";
    public static final String PRODUCT = "PRODUCT";
    public static final String MEASURE = "MEASURE";
    public static final String CLIENT = "CLIENT";
    public static final String LIST_PRODUCTS = "LIST_PRODUCTS";
    public static final String VALUE_PAY = "VALUE_PAY";
    public static final String RESULT_CALC = "RESULT_CALC";
    public static final String LIST_MEASURE = "LIST_MEASURE";
    private static String OLD_POSITION;

    private ViewPager pager;
    private int oldPosition;
    private double quantity;
    private SlidingTabLayout tabsVenda;
    private String[] reciverText = new String[]{IDENTIFIER_SELL_PAYMENT, IDENTIFIER_SELL_PRINCIPAL};
    private DrawerLayout drawer;
    private Product product;
    private Client client;
    private MeasureDataSet measure;
    ArrayList<Product> productsDatas;
    HashMap<Integer, ArrayList<Measure>> measureDadas;
    private DaoProduct daoProduct;
    private Money valuePay;
    private ItemSell resultCalculated;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle restore)
    {
        if(restore == null) createValues();
        else  restore(restore);

        //Create fragments
        SellCarStep principal = new SellCarStep();
        SellClientStep client = new SellClientStep();
        SellPayment payment =  new SellPayment();
        super.addStep(principal);
        super.addStep(client);
        super.addStep(payment);

        super.setTitle("Vendas");
        setPreviousVisible();

        super.onCreate(restore);

        this.toolbar = super.getToolbar();
        setSupportActionBar(toolbar);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void createValues()
    {
        this.daoProduct = new DaoProduct(this);
        this.client = SellClientFragment.DEFAULT_CLIENT;
        this.productsDatas = new ArrayList<>();
        this.measureDadas = new HashMap<>();
        daoProduct.loadProducts(new OnProcess<Product>()
        {
            @Override
            public void process(Product product)
            {
                measureDadas.put(product.getId(), daoProduct.loadMetreages(product.getId()));
                productsDatas.add(product);
            }
        });
        CallbackControler.inNet(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle save)
    {
        super.onSaveInstanceState(save);
        save.putSerializable(LIST_PRODUCTS, this.productsDatas);
        save.putSerializable(LIST_MEASURE, this.measureDadas);

        save.putSerializable(CLIENT, this.client);
        save.putDouble(QUANTITY, this.quantity);
        save.putSerializable(PRODUCT, this.product);
        save.putSerializable(MEASURE, this.measure);
        save.putSerializable(SellStepperActivity.OLD_POSITION, this.oldPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            this.finish();
        return true;
    }

    private void restore(Bundle restore)
    {
        this.daoProduct = new DaoProduct(this);
        this.oldPosition = restore.getInt(SellStepperActivity.OLD_POSITION);
        CallbackControler.outNet(this);
        this.productsDatas = (ArrayList<Product>) restore.getSerializable(LIST_PRODUCTS);
        this.measureDadas = (HashMap<Integer, ArrayList<Measure>>) restore.getSerializable(LIST_MEASURE);
//        this.client = (Client) restore.getSerializable(CLIENT);
        this.quantity = restore.getDouble(QUANTITY);
        this.product = (Product) restore.getSerializable(PRODUCT);
        this.measure = (MeasureDataSet) restore.getSerializable(MEASURE);
        this.oldPosition = restore.getInt(OLD_POSITION);
        Log.e("DBA:APP.TEST", getClass().getSimpleName()+"-> RESTORE SUPPORT DATA");
        CallbackControler.outNet(this);
        CallbackControler.inNet(this);
    }

    @Override
    public void onReceive(SendType sendType, CallbackClient origem, String summary, Object[] values)
    {
        String keyOrigen = origem.getProtocolKey().toString();

        switch (keyOrigen)
        {
            case IDENTIFIER_SELL_PAYMENT:
                intentSellFinal(summary);
                return;
            case IDENTIFIER_SELL_HOME:
                intentSellHome(summary, values);
                return;
            case IDENTIFIER_SELL_PRINCIPAL:
                intentPrincipal(summary, values);
                return;
            case IDENTIFIER_SELL_CLIENT_REGISTER:
                intentClientRegister(summary, values);
                return;
            case IDENTIFIER_SELL_CLIENT_LIST:
                intentClientList(summary, values);
                return;
        }
    }

    private void intentClientList(String summary, Object[] values)
    {
        Log.i("DBA:APP.TEST", getClass().getSimpleName()+"-> INTENT FOR CLinetList RECIVIED summary:\""+summary+"\" value:\""+ DMLite.toText(values)+"\"");
        if(summary.equals(SUMMARY_CLIENT_CHANGED))
        {
            this.client = (Client) values[0];
        }
    }

    private void intentClientRegister(String summary, Object[] values)
    {
        this.client = (Client) values[0];
        nextSteep();
    }

    /**
     * O proximo passo a se navegar
     */
    private void nextSteep()
    {
        if(this.quantity >0
                && product != null
                && this.measure != null);
    }

    private void intentPrincipal(String summary, Object[] values)
    {
        if(summary.equals(SUMMARY_PRODUCT_CHAGE))
        {
            this.product = (Product) values[0];
            this.measure = (MeasureDataSet) values[1];
        }
    }

    private void intentSellHome(String summary, Object[] values)
    {
        if(summary.equals(SUMMARY_OPEN_DRAWER))
        {
            this.drawer.openDrawer(Gravity.LEFT, true);
        }
        else if(summary.equals(SUMMARY_NAVE_PAGER))
        {
            String pagerKey = (String) values[0];
        }
    }

    private void intentSellFinal(String summary)
    {
        if(summary.equals(SUMMARY_NAVE_MY))
        {
        }
        else if(summary.equals(RMap.SUMMARY_NEW_REGISTER_SUCCESS))
        {
            this.client = SellClientFragment.DEFAULT_CLIENT;
            this.quantity = 0.0;
            this.measure.setSelected(false);
            this.measure = null;
            this.product = null;
        }
    }


    @Override
    public Bundle query(CallbackClient cliente, String queryQuention, Object... inParams) {
        Bundle bundle = new Bundle();

        switch (queryQuention)
        {
            case QUERY_DATA_CREATOR:
                bundle.putDouble(SellStepperActivity.QUANTITY, this.quantity);
                bundle.putSerializable(SellStepperActivity.PRODUCT, this.product);
                bundle.putSerializable(SellStepperActivity.MEASURE, this.measure);
                bundle.putSerializable(CLIENT, this.client);
                bundle.putSerializable(VALUE_PAY, this.valuePay);
                break;
            case RMap.QUERY_LIST_PRODUCTS:
                bundle.putSerializable(LIST_PRODUCTS, this.productsDatas);
                bundle.putSerializable(LIST_MEASURE, this.measureDadas);
                break;
            case QUERY_CLIENT_SELECTED:
                bundle.putSerializable(CLIENT, this.client);
        }
        return bundle;
    }


    @Override
    public CharSequence getProtocolKey()
    {
        return RMap.IDENTIFIER_SELL_SUPPORT;
    }

    public static NumberFormat instanceFormatterQuantity()
    {
        NumberFormat fmt = NumberFormat.getInstance(new Locale("PT", "st"));
        fmt.setMaximumFractionDigits(1);
        fmt.setMinimumFractionDigits(1);
        fmt.setMaximumIntegerDigits(15);
        fmt.setMinimumIntegerDigits(1);
        fmt.setCurrency(Currency.getInstance("STD"));
        fmt.setRoundingMode(RoundingMode.FLOOR);
        return fmt;
    }

    public static NumberFormat instanceFormatterMoney()
    {
        NumberFormat fmt = NumberFormat.getInstance(new Locale("PT", "st"));
        fmt.setMaximumFractionDigits(2);
        fmt.setMinimumFractionDigits(2);
        fmt.setMinimumIntegerDigits(1);
        return fmt;
    }
}
