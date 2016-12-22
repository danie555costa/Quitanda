package st.domain.quitanda.client.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import st.domain.support.android.model.ItemFragment;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.view.callbaks.OnPaymentChoseListiner;

/**
 * Created by Daniel Costa at 8/27/16.
 * Using user computer xdata
 */
public class SellPaymentNow extends Fragment implements ItemFragment, OnPaymentChoseListiner
{
    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_sell_payment_now, container, false);
        return this.rootView;
    }

    @Override
    public CharSequence getTitle() {
        return "Agora";
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public CharSequence getProtocolKey()
    {
        return  "YHhsn";
    }

    @Override
    public void accept(Activity activity) {
//        PUT HERE THE RESULT OF RETUR FOR PAYAMENT TYPE
//        activity.setResult();
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public String invalidMessage() {
        return null;
    }
}
