package com.st.ggviario.client.view.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.st.dbutil.android.fragment.GeralActivityPager;
import com.st.dbutil.android.view.SlidingTabLayout;
import com.st.ggviario.client.R;
import com.st.ggviario.client.view.fragments.SellPaymentCredit;
import com.st.ggviario.client.view.fragments.SellPaymentNow;
import com.st.ggviario.client.view.fragments.SellPaymentQueue;

/**
 * Created by Daniel Costa at 8/27/16.
 * Using user computer xdata
 */
public class PaymentMode extends GeralActivityPager
{
    private ViewPager pager;
    private SlidingTabLayout slidingLayout;
    private Toolbar tollbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_payment);


        this.pager = (ViewPager) this.findViewById(R.id.pager_payment_mode);
        this.slidingLayout = (SlidingTabLayout) this.findViewById(R.id.tabs_payment_mode);
        this.tollbar = (Toolbar) this.findViewById(R.id.toolbar_top);


        this.tollbar.setTitle(R.string.app_name);
        this.setSupportActionBar(this.tollbar);

        SellPaymentNow paymentNow = new SellPaymentNow();
        SellPaymentCredit paymentCredit = new SellPaymentCredit();
        SellPaymentQueue queue = new SellPaymentQueue();

        this.slidingLayout.useColorizerOnTextColor(true);

        super.addFragment(paymentNow);
        super.addFragment(paymentCredit);
        super.addFragment(queue);

        super.setViewPager(this.pager);
        super.setTabLayout(this.slidingLayout);
        super.setDistributeEvenly(false);
        super.setSelectedIndicatorColor(getResources().getColor(R.color.white));
        super.setDistributeEvenly(true);
        super.setUp();

        //Treat the notifyCar bar here
        super.setSupportActionBar(this.tollbar);
        ActionBar actionBar = super.getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Modo Pagamento");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_payement_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home)
            this.finish();
        return true;
    }
}
