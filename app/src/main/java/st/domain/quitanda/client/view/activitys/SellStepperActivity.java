package st.domain.quitanda.client.view.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.github.fcannizzaro.materialstepper.style.TabStepper;
import st.domain.quitanda.client.model.visitor.SellCollectorVisitor;
import st.domain.quitanda.client.model.visitor.Collectable;
import st.domain.quitanda.client.view.fragments.SellCarStep;
import st.domain.quitanda.client.view.fragments.SellClientStep;
import st.domain.quitanda.client.view.fragments.SellPayment;

public class SellStepperActivity extends TabStepper implements Collectable
{
    public static final String CLIENT = "CLIENT";

    private Toolbar toolbar;
    private SellCarStep sellCarStep;
    private SellClientStep client;
    private SellPayment payment;

    @Override
    protected void onCreate(Bundle restore)
    {
        //Create fragments
        this.sellCarStep = new SellCarStep();
        this.client = new SellClientStep();
        this.payment =  new SellPayment();

        super.addStep(sellCarStep);
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

    @Override
    public void onComplete() {
        super.onComplete();
    }

    @Override
    public void onComplete(Bundle data) {
        Log.i("DBA:APP.TEST", "On finish activity");
        super.onComplete(data);
        SellCollectorVisitor collector;
        this.accept(collector = new SellCollectorVisitor());
    }

    @Override
    public void accept(SellCollectorVisitor collectorVisitor) {
        this.sellCarStep.accept(collectorVisitor);
        this.client.accept(collectorVisitor);
        this.payment.accept(collectorVisitor);

        Log.i("DBA:APP.TEST", "Collection result "+collectorVisitor.toString());
    }
}

