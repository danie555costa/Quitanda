package com.st.ggviario.client.view.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.st.ggviario.client.R;
import com.st.ggviario.client.dao.DaoProduct;
import com.st.ggviario.client.model.Car;
import com.st.ggviario.client.model.ItemSell;
import com.st.ggviario.client.model.Measure;
import com.st.ggviario.client.model.PriceCalculator;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.model.builders.CarBuilder;
import com.st.ggviario.client.model.builders.ProductBuilder;
import com.st.ggviario.client.model.contract.ObserverCalculated;
import com.st.ggviario.client.view.adapters.SupportCalculator;
import com.st.ggviario.client.view.adapters.dataset.CalculatorDataSet;
import com.st.ggviario.client.view.adapters.dataset.MeasureDataSet;
import com.st.ggviario.client.view.events.CarEvemtAction;
import com.st.ggviario.client.view.events.CloseActivityEvent;
import com.st.ggviario.client.view.events.MenuObserver;
import com.st.ggviario.client.view.fragments.SellCarStep;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel Costa on 8/13/16.
 * User computer: Daniel Costa
 */
public class CalculatorActivity extends AppCompatActivity implements SupportCalculator.OnClickKeyboardListener, SupportCalculator.OnClickMeasureListener{
    private DaoProduct daoProduct;
    private Toolbar toolbar;
    private SupportCalculator supportAdapter;
    private Product product;
    private  ArrayList<MenuObserver> list;
    private Car car;
    private CarEvemtAction carAction;

    @Override
    protected void onCreate(@Nullable Bundle restoreInstance)
    {
        super.onCreate(restoreInstance);
        super.setContentView(R.layout.activity_calculator);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_calculator);
        this.toolbar  = (Toolbar) findViewById(R.id.toolbar_top);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.daoProduct = new DaoProduct(this);
        this.supportAdapter = new SupportCalculator(CalculatorActivity.this);
        this.list = new ArrayList<>();

        this.prepareValues(restoreInstance);

        this.carAction = new CarEvemtAction(this.car, this.product);
        this.list.add(new CloseActivityEvent());
        this.list.add(this.carAction);


        this.toolbar.setTitle(product);
        this.toolbar.inflateMenu(R.menu.menu_calculator);

        this.setSupportActionBar(toolbar);
        if(this.getSupportActionBar() != null)
        {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setTitle(product);
            this.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }

        recyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        prepareSupport(recyclerView);
    }

    private void prepareValues(@Nullable Bundle restoreInstance)
    {
        restoreInstance = (restoreInstance == null)?
                getIntent().getExtras(): restoreInstance;

        this.product = loadProduct(restoreInstance);
        this.car = new CarBuilder().buildFromXML(restoreInstance.getString(SellCarStep.CAR));
    }

    private void prepareSupport(final RecyclerView recyclerView)
    {
        Thread exec = new Thread(new Runnable() {
            @Override
            public void run()
            {
                final ArrayList<Measure> list = loadDatas(product);
                CalculatorActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        supportAdapter.setListMeasure(list);
                        supportAdapter.setOnClickKeyboardListner(CalculatorActivity.this);
                        supportAdapter.setOnclickMeasureListener(CalculatorActivity.this);
                        recyclerView.setAdapter(supportAdapter.getCreatedSupport());
                    }
                });
            }
        });
        exec.start();
    }

    private Product loadProduct(Bundle bundle)
    {
        ProductBuilder  builder = new ProductBuilder();
        Product product = builder.buildFromXML(bundle.getString(SellCarStep.PRODUCT));
        return  product;
    }

    private ArrayList<Measure>  loadDatas(Product product)
    {
        ArrayList<Measure> list;
        list = daoProduct.loadMetreages(product.getId());
        return list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.i("DBA:APP.TEST", "onOptionsItemSelected this list size is "+this.list.size());
        for(MenuObserver menuItemSelected: this.list)
        {
            if(menuItemSelected.accept(item, this)) return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    @Override
    public void onClick(View view, char key, CalculatorDataSet data) {
        this.calc();
    }

    @Override
    public void onClickAction(View view, SupportCalculator.KeyboardAction action, CalculatorDataSet data) {
        this.calc();
    }

    @Override
    public void onClickMeasure(View view, MeasureDataSet dataMeasure)
    {
        calc();
    }

    private void calc()
    {
        MeasureDataSet dataMeasure = this.supportAdapter.getCurrentDataMeasure();
        double value = supportAdapter.getKeyboardValue();
        if(value > 0
                && dataMeasure != null
                && dataMeasure.isSelected())
        {
            PriceCalculator priceCalculator = new PriceCalculator(new DaoProduct(this));

            priceCalculator.idMetreageFrom(dataMeasure.getMeasuere())
                    .idProduct(this.product.getId())
                    .quantity(value)
                    .addOnCalculated(carAction)
                    .addOnCalculated(new ObserverCalculated() {
                        @Override
                        public void accept(ItemSell itemSell) {
                            if(itemSell != null)
                            {
                                supportAdapter.setPrice(itemSell.getAmountPay());
                            }
                        }
                    })
                    .calc();
        }
        else this.supportAdapter.setPrice(0.0);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    private class CalculatorResult implements Serializable
    {
        private final double quantity;
        private final double montate;
        private final Measure seleced;
        private final Product selecedProduct;

        public CalculatorResult(double quantity, double montate, Measure seleced, Product selecedProduct) {
            this.quantity = quantity;
            this.montate = montate;
            this.seleced = seleced;
            this.selecedProduct = selecedProduct;
        }

        public double getQuantity() {
            return quantity;
        }

        public double getMontate() {
            return montate;
        }

        public Measure getSeleced() {
            return seleced;
        }

        public Product getSelecedProduct() {
            return selecedProduct;
        }
    }
}
