package com.st.ggviario.client.view.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.github.fcannizzaro.materialstepper.style.TabStepper;

//import ivb.com.materialstepper.progressMobileStepper;
//import ivb.com.materialstepper.simpleMobileStepper;
//import ivb.com.materialstepper.components.stepperView;

/**
 * Created by xdata on 8/12/16.
 */

// TextStepper, ProgressStepper, DotStepper, TabStepper
public class Harvest extends TabStepper
{

    private int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        boolean linear = getIntent().getBooleanExtra("linear", false);

        setErrorTimeout(1500);
        setTitle("Dots Stepper");



        setLinear(false);

        setPreviousVisible();

//        setDisabledTouch();

        setAlternativeTab(false);

        super.onCreate(savedInstanceState);


        this.mError.setText(Html.fromHtml(this.mSteps.getCurrent().error()));

    }

    private AbstractStep createFragment(AbstractStep fragment)
    {
        Bundle b = new Bundle();
        b.putInt("position", i++);
        fragment.setArguments(b);
        return fragment;
    }

    private Toolbar toolbar;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
////        this.setContentView(R.layout.activity_harvest);
////        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar_top);
//
////        this.setSupportActionBar(this.toolbar);
////        ActionBar actionBar = this.getSupportActionBar();
////        if (actionBar != null)
////        {
////            actionBar.setDisplayHomeAsUpEnabled(true);
////        }
//    }

//    @Override
//    public void onStepperCompleted() {
//
//    }

//    @Override
//    public List<Class> init()
//    {
//        ArrayList<Class> list = new ArrayList<>();
//        list.addItemDataSet(MainHome.class);
//        list.addItemDataSet(MainHome.class);
//        list.addItemDataSet(MainHome.class);
//        return list;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home) this.finish();
        return true;
    }
}
