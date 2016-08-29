package com.st.ggviario.client.view.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.st.dbutil.android.model.CallbackClient;
import com.st.ggviario.client.R;
import com.st.ggviario.client.dao.DaoProduct;
import com.st.ggviario.client.model.Measure;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.view.adapters.SupportCalculator;
import com.st.ggviario.client.view.adapters.dataset.CalculatorDataSet;
import com.st.ggviario.client.view.adapters.dataset.MeasureDataSet;
import com.st.ggviario.client.view.fragments.SellCarStep;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel Costa on 8/13/16.
 * User computer: Daniel Costa
 */
public class Calculator extends AppCompatActivity implements SupportCalculator.OnClickKeyboarListner, SupportCalculator.OnClickMeasureListener, CallbackClient {
    private SupportCalculator supportAdapter;
    private Product product;
    private DaoProduct daoProduct;
    private ArrayList<Measure> list;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_calculator);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_calculator);
        this.toolbar  = (Toolbar) findViewById(R.id.toolbar_top);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.daoProduct = new DaoProduct(this);

        Bundle paramns = this.getIntent().getExtras();

        if(paramns != null && paramns.containsKey(SellCarStep.PRODUCT))
        {
            this.product = (Product) paramns.getCharSequence(SellCarStep.PRODUCT);
            if(product == null)
            {
                finish();
                return;
            }
            this.list = daoProduct.loadMetreages(product.getId());
        }
        else
        {
            finish();
            return;
        }

        this.supportAdapter = new SupportCalculator(this);
        this.supportAdapter.setListMeasure(list);

        recyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
//                int lastCompletePosition;

//                lastCompletePosition = layoutManager.findLastCompletelyVisibleItemPosition();

//                if(lastCompletePosition +1 == supportAdapter.getCreatedSupport().getItemCount())
//                {
//
//                }
            }
        });

        recyclerView.setAdapter(this.supportAdapter.getCreatedSupport());
        this.supportAdapter.setOnClickKeyboardListner(this);
        this.supportAdapter.setOnclickMeasureListener(this);

        this.toolbar.setTitle(this.product);
        this.toolbar.inflateMenu(R.menu.menu_calculator);

        this.setSupportActionBar(toolbar);
        if(this.getSupportActionBar() != null)
        {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setTitle(product);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            this.finishAsResult();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    private void finishAsResult()
    {
        this.finish();
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
        MeasureDataSet dataMeasure = supportAdapter.getCurrentDataMeasure();
        double value = supportAdapter.getKeyboardValue();
        if(value > 0
                && dataMeasure != null
                && dataMeasure.isSelected())
            this.daoProduct.calcPrice(product.getId(), value, dataMeasure.getIdMetreage(), this);
        else this.supportAdapter.setPrice(0.0);
    }

    @Override
    public void onReceive(SendType sendType, CallbackClient origem, String summary, Object[] values) {
        if(origem == this)
        {
            DaoProduct.ResultPrice resultPrice = (DaoProduct.ResultPrice) values[0];
            if(resultPrice != null)
            {
                this.supportAdapter.setPrice(resultPrice.valueFinalPagar);
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    public Bundle query(CallbackClient clientOrigen, String queryQuention, Object... inParams) {
        return null;
    }

    @Override
    public CharSequence getProtocolKey() {
        return null;
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
