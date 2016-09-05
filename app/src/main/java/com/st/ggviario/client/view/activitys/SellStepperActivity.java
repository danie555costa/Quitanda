package com.st.ggviario.client.view.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.fcannizzaro.materialstepper.style.TabStepper;
import com.st.ggviario.client.references.RMap;
import com.st.ggviario.client.view.fragments.SellCarStep;
import com.st.ggviario.client.view.fragments.SellClientStep;
import com.st.ggviario.client.view.fragments.SellPayment;

public class SellStepperActivity extends TabStepper implements RMap
{
    public static final String CLIENT = "CLIENT";

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle restore)
    {
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

    @Override
    protected void onSaveInstanceState(Bundle save)
    {
        super.onSaveInstanceState(save);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            this.finish();
        return true;
    }
}

