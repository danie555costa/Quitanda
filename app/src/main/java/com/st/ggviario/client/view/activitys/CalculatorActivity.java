package com.st.ggviario.client.view.activitys;

import android.content.Intent;
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
import com.st.ggviario.client.model.ItemSell;
import com.st.ggviario.client.model.Measure;
import com.st.ggviario.client.model.PriceCalculator;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.model.builders.ItemSellBuilder;
import com.st.ggviario.client.model.builders.MeasureBuilder;
import com.st.ggviario.client.model.builders.ProductBuilder;
import com.st.ggviario.client.model.contract.ObserverCalculated;
import com.st.ggviario.client.references.RMap;
import com.st.ggviario.client.view.adapters.vholders.SupportAdapter;
import com.st.ggviario.client.view.adapters.vfactory.CalculatorViewHolderFactory;
import com.st.ggviario.client.view.adapters.vfactory.MeasureViewHolderFactory;
import com.st.ggviario.client.view.adapters.dataset.CalculatorDataSet;
import com.st.ggviario.client.view.adapters.dataset.MeasureDataSet;
import com.st.ggviario.client.view.adapters.vholders.CalculatorViewHolder;
import com.st.ggviario.client.view.adapters.vholders.MeasureViewHolder;
import com.st.ggviario.client.view.events.CarActionEvent;
import com.st.ggviario.client.view.events.CloseActivityEvent;
import com.st.ggviario.client.view.events.MenuMapper;
import com.st.ggviario.client.view.fragments.SellCarStep;

import java.util.ArrayList;

/**
 * Created by Daniel Costa on 8/13/16.
 * User computer: Daniel Costa
 */
public class CalculatorActivity extends AppCompatActivity implements CalculatorViewHolder.OnClickKeyboardListener, MeasureViewHolder.OnClickMeasureListener, CalculatorViewHolderFactory.OnCreateCalculatorViewHolder {
    private static final String MEASURE_LIST = "MEASURE_LIST";
    private DaoProduct daoProduct;
    private Toolbar toolbar;
    private SupportAdapter supportAdapter;
    private Product product;
    private ArrayList<Measure> listMeasure;
    private CalculatorViewHolder calculator;
    private ItemSell lastCreated;
    private MenuMapper menuMapper;
    private CarActionEvent carActionEvent;
    private boolean haveInCar;
    private Measure measure;
    private double quantity;
    private MeasureDataSet measureDataSet;

    @Override
    protected void onCreate(@Nullable Bundle restoreInstance)
    {
        super.onCreate(restoreInstance);
        super.setContentView(R.layout.activity_calculator);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_calculator);
        this.toolbar  = (Toolbar) findViewById(R.id.toolbar_top);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.daoProduct = new DaoProduct(this);
        this.supportAdapter = new SupportAdapter(this);
        this.menuMapper = new MenuMapper(this);


        this.prepareValues(restoreInstance);


        this.supportAdapter.addViewHolderFactory(new CalculatorViewHolderFactory()
                .setOnKeyboardClickListener(this)
                .setOnCreateCalculator(this));

        this.supportAdapter.addViewHolderFactory(new MeasureViewHolderFactory()
                .setOnClickMeasureListener(this));
        this.supportAdapter.addDataSet(new CalculatorDataSet(13, this.quantity));




        this.carActionEvent = new CarActionEvent(this.product, this.haveInCar);

        menuMapper.add(this.carActionEvent);
        menuMapper.add(new CloseActivityEvent(new CloseActivityEvent.OnFinishing() {
            @Override
            public void onFinish() {

                Bundle bundle = new Bundle();
                ItemSellBuilder sellBuilder = new ItemSellBuilder();
                if(lastCreated == null)
                    lastCreated = sellBuilder.product(product)
                            .build();

                bundle.putString(RMap.ITEM_SELL, sellBuilder.toXml(lastCreated));
                bundle.putSerializable(RMap.CAR_ACTION, carActionEvent.getCarAction());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(10, intent);
                finish();
            }
        }));

        prepareToolbar();

        recyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        prepareSupport(recyclerView);
    }

    private void prepareToolbar()
    {
        this.toolbar.setTitle(product);
        this.toolbar.inflateMenu(R.menu.menu_calculator);

        this.setSupportActionBar(toolbar);
        if(this.getSupportActionBar() != null)
        {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setTitle(product);
            this.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }
    }

    private void prepareValues(@Nullable Bundle restore)
    {
        restore = (restore == null)?
                getIntent().getExtras(): restore;

        this.product = loadProduct(restore);
        this.haveInCar = restore.getBoolean(RMap.HAS_IN_CAR);
        String xml = restore.getString(RMap.ITEM_SELL_MEASURE);

        if(haveInCar)
        {
            MeasureBuilder measureBuilder = new MeasureBuilder();
            this.measure = measureBuilder.buildFromXML(xml);
            this.quantity = restore.getDouble(RMap.ITEM_SELL_QUANTITY, 0);
            this.measureDataSet = new MeasureDataSet(measure);
            this.measureDataSet.setSelected(true);
        }

        Log.i("DBA:APP.TEST", "have in car recived result " +haveInCar);
        if(restore.containsKey(RMap.ITEM_SELL)) {
            ItemSellBuilder sellBuilder = new ItemSellBuilder();
            this.lastCreated = sellBuilder.buildFromXML(restore.getString(RMap.ITEM_SELL));
        }
    }

    private void prepareSupport(final RecyclerView recyclerView)
    {
        Thread exec = new Thread(new Runnable() {
            @Override
            public void run()
            {
                final ArrayList<MeasureDataSet> list = new ArrayList<>();
                for(Measure iMeasure:  loadData(product))
                {
                    MeasureDataSet me;
                    list.add(me = new MeasureDataSet(iMeasure));
                    if(measure != null
                            && iMeasure.equals(measure))
                        me.setSelected(true);
                }

                CalculatorActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        supportAdapter.addAll(list);
                        recyclerView.setAdapter(supportAdapter);
                    }
                });
            }
        });
        exec.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("DBA:APP.TEST", "onResume");
        if(calculator == null)
            calculator = (CalculatorViewHolder) supportAdapter.getViewHolderIfAvailable(0);
        if(calculator != null)
            Log.i("DBA:APP.TEST", "Measures is loaded and calculator is created onResume");
        calc();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i("DBA:APP.TEST", "onPostCreate");
        if(calculator == null)
            calculator = (CalculatorViewHolder) supportAdapter.getViewHolderIfAvailable(0);
        if(calculator != null)
            Log.i("DBA:APP.TEST", "Measures is loaded and calculator on onPostCreate");
        calc();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i("DBA:APP.TEST", "onPostCreate");
        if(calculator == null)
            calculator = (CalculatorViewHolder) supportAdapter.getViewHolderIfAvailable(0);
        if(calculator != null)
            Log.i("DBA:APP.TEST", "Measures is loaded and calculator on onPostCreate");
        calc();
    }

    private Product loadProduct(Bundle bundle)
    {
        ProductBuilder  builder = new ProductBuilder();
        Product product = builder.buildFromXML(bundle.getString(SellCarStep.PRODUCT));
        return  product;
    }

    private ArrayList<Measure> loadData(Product product)
    {
        this.listMeasure = daoProduct.loadMetreages(product.getId());
        return listMeasure;
    }

    @Override
    protected void onSaveInstanceState(Bundle backup) {
        super.onSaveInstanceState(backup);
        ProductBuilder productBuilder = new ProductBuilder();
        ItemSellBuilder sellBuilder = new ItemSellBuilder();
        MeasureBuilder measureBuilder = new MeasureBuilder();
        backup.putString(SellCarStep.PRODUCT, productBuilder.toXml(this.product));
        backup.putString(RMap.ITEM_SELL, sellBuilder.toXml(this.lastCreated));
        backup.putString(MEASURE_LIST, measureBuilder.listAsXml(this.listMeasure));
        backup.putBoolean(RMap.HAS_IN_CAR, this.haveInCar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return this.menuMapper.menu(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        this.carActionEvent.onMenu(menu);
        return true;
    }

    @Override
    public void onClickKeyboard(View view, char key, CalculatorViewHolder data) {
        this.calculator = data;
        this.quantity = data.getValue();
        Log.i("DBA:APP.TEST", "onClickKeyboard keyboard");
        this.calc();
    }

    @Override
    public void onClickKeyboardAction(View view, CalculatorViewHolder.KeyboardAction action, CalculatorViewHolder data) {
        this.calculator = data;
        this.quantity = data.getValue();
        Log.i("DBA:APP.TEST", "onClickKeyboard keyboard notifyCar");
        this.calc();
    }

    @Override
    public void onClickMeasure(View view, MeasureDataSet dataMeasure)
    {
        this.measure = dataMeasure.getMeasure();
        this.measureDataSet = dataMeasure;
        Log.i("DBA:APP.TEST", "onClickKeyboard measure");
        calc();
    }

    private void calc()
    {
        Log.i("DBA:APP.TEST", "on calc");
        if(this.calculator != null
                && this.quantity >=0
                && this.measureDataSet != null
                && this.measureDataSet.isSelected()
            )
        {
            Log.i("DBA:APP.TEST", "On pre calculate price");
            PriceCalculator priceCalculator = new PriceCalculator(new DaoProduct(this));

            priceCalculator.product(this.measure)
                    .product(this.product)
                    .quantity(this.quantity)
                    .onCalculated(this.carActionEvent)
                    .onCalculated(new ObserverCalculated() {
                        @Override
                        public void accept(ItemSell itemSell) {
                            Log.i("DBA:APP.TEST", "On calculated price");
                            if(itemSell != null)
                            {
                                Log.i("DBA:APP.TEST", "Calculated price "+itemSell.getAmountPay());
                                calculator.setPreviewPrice(itemSell.getAmountPay());
                                lastCreated = itemSell;
                            }
                        }
                    })
                    .calc();
        }
        else if(this.calculator != null)
            calculator.setPreviewPrice(0.0);
    }

    @Override
    public void onCreateCalculatorViewHolder(CalculatorViewHolder calculatorViewHolder) {
        this.calculator = calculatorViewHolder;
        this.calc();
    }
}
