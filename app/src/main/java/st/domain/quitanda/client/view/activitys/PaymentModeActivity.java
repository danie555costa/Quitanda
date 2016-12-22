package st.domain.quitanda.client.view.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import st.domain.support.android.fragment.GeralActivityPager;
import st.domain.support.android.view.SlidingTabLayout;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.view.callbaks.OnPaymentChoseListiner;
import st.domain.quitanda.client.view.events.CloseActivityEvent;
import st.domain.quitanda.client.view.events.MenuMapper;
import st.domain.quitanda.client.view.fragments.SellPaymentCredit;
import st.domain.quitanda.client.view.fragments.SellPaymentNow;
import st.domain.quitanda.client.view.fragments.SellPaymentQueue;

/**
 * Created by Daniel Costa at 8/27/16.
 * Using user computer xdata
 */
public class PaymentModeActivity extends GeralActivityPager implements ViewPager.OnPageChangeListener {
    private ViewPager pager;
    private SlidingTabLayout slidingLayout;
    private Toolbar tollbar;
    MenuMapper menuMapper;
    private OnPaymentChoseListiner onPaymentChoseListiner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_payment);


        this.pager = (ViewPager) this.findViewById(R.id.pager_payment_mode);
        this.slidingLayout = (SlidingTabLayout) this.findViewById(R.id.tabs_payment_mode);
        this.tollbar = (Toolbar) this.findViewById(R.id.toolbar_top);
        this.menuMapper = new MenuMapper(this);
        this.menuMapper.add(new CloseActivityEvent());

        CloseActivityEvent done;
        this.menuMapper.add(done = new CloseActivityEvent());
        done.setMenuId(R.id.opt_paymode_done);


        this.tollbar.setTitle(R.string.app_name);
        this.setSupportActionBar(this.tollbar);

        SellPaymentNow paymentNow = new SellPaymentNow();
        SellPaymentCredit paymentCredit = new SellPaymentCredit();
        SellPaymentQueue queue = new SellPaymentQueue();

        this.slidingLayout.useColorizerOnTextColor(true);

        super.addFragment(paymentNow);
        super.addFragment(paymentCredit);
        super.addFragment(queue);

        this.pager.addOnPageChangeListener(this);
        super.setViewPager(this.pager);
        super.setTabLayout(this.slidingLayout);
        super.setDistributeEvenly(false);
        super.setSelectedIndicatorColor(getResources().getColor(R.color.white));
        super.setDistributeEvenly(true);
        super.setUp();

        super.setSupportActionBar(this.tollbar);
        this.tollbar.setTitleTextColor(getResources().getColor(R.color.white));
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
        return this.menuMapper.menuAction(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        this.onPaymentChoseListiner = (OnPaymentChoseListiner) super.getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state){

    }
}
