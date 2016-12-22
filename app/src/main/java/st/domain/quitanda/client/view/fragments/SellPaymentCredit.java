package st.domain.quitanda.client.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import st.domain.support.android.model.ItemFragment;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.references.RMap;
import st.domain.quitanda.client.util.components.DatePickerControl;
import st.domain.quitanda.client.util.components.OnChangeCalendar;
import st.domain.quitanda.client.view.callbaks.OnPaymentChoseListiner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

/**
 * Created by Daniel Costa at 8/27/16.
 * Using user computer xdata
 */
public class SellPaymentCredit extends Fragment implements ItemFragment, OnPaymentChoseListiner {
    private View rootView;
    private ImageButton ibtEditDatePayment;
    private ImageButton ibtEditDateSend;

    private DatePickerControl dateSendControl;
    private DatePickerControl datePayControl;
    private TextView tvDatePay;
    private TextView tvDateSend;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_sell_payment_credito, container, false);
        this.ibtEditDatePayment = (ImageButton) this.rootView.findViewById(R.id.ibt_edit_date_payment);
        this.ibtEditDateSend = (ImageButton) this.rootView.findViewById(R.id.ibt_edit_date_send);
        this.tvDatePay = (TextView) this.rootView.findViewById(R.id.tv_date_pay);
        this.tvDateSend = (TextView) this.rootView.findViewById(R.id.tv_date_send);

        this.datePayControl = new DatePickerControl("Pagamento");
        this.dateSendControl = new DatePickerControl("Entrega");

        this.datePayControl.addOnChangeCalendar(new OnChangeCalendar() {
            @Override
            public void accept(DatePickerControl calendar) {
                tvDatePay.setText(calendar.getData("dd 'de' MMMM 'de' yyyy"));
            }
        });

        this.dateSendControl.addOnChangeCalendar(new OnChangeCalendar() {
            @Override
            public void accept(DatePickerControl calendar) {
                tvDateSend.setText(calendar.getData("dd 'de' MMMM 'de' yyyy"));
            }
        });


        this.ibtEditDatePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                opemDialog(datePayControl);
            }
        });

        this.ibtEditDateSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opemDialog(dateSendControl);
            }
        });

        return rootView;
    }

    private void opemDialog(DatePickerControl datePickerControl)
    {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(datePickerControl,
                datePickerControl.getYear(),
                datePickerControl.getMonth(),
                datePickerControl.getDay());

        datePickerDialog.show(getActivity().getFragmentManager(), datePickerControl.getLabel());
    }

    @Override
    public CharSequence getTitle()
    {
        return "Credito";
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public CharSequence getProtocolKey()
    {
        return RMap.IDENTIFIER_SELL_PAY_CREDIT;
    }

    @Override
    public boolean isValid()
    {
        return false;
    }

    @Override
    public String invalidMessage() {
        return "Falta data de pagamento";
    }

    @Override
    public void accept(Activity activity) {
    }
}
