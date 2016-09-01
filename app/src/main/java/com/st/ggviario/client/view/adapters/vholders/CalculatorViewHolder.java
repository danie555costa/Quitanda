package com.st.ggviario.client.view.adapters.vholders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.dbutil.android.adapter.SupportRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.view.activitys.SellStepperActivity;
import com.st.ggviario.client.view.adapters.SupportCalculator;
import com.st.ggviario.client.view.adapters.dataset.CalculatorDataSet;

import java.text.NumberFormat;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class CalculatorViewHolder extends BaseRecyclerAdapter.ItemViewHolder implements View.OnClickListener
{
    private static final NumberFormat FMT = SellStepperActivity.instanceFormatterMoney();
    private final int [] buttons = {
            R.id.bt_keyboard_0,
            R.id.bt_keyboard_0,
            R.id.bt_keyboard_1,
            R.id.bt_keyboard_2,
            R.id.bt_keyboard_3,
            R.id.bt_keyboard_4,
            R.id.bt_keyboard_5,
            R.id.bt_keyboard_6,
            R.id.bt_keyboard_7,
            R.id.bt_keyboard_8,
            R.id.bt_keyboard_9,
            R.id.bt_keyboard_p,
            R.id.bt_keyboard_clear,
            R.id.bt_keyboard_backspace
    };

    private final Button buttonClear;
    private final ImageButton buttonBackspace;
    private final TextView tvCalcView;
    private final ImageButton btClose;
    private final View panelView;
    private final TextView tvPreviewValue;
    private CalculatorDataSet values;
    private SupportCalculator.OnClickKeyboardListener onClickKeyboarListner;

    /**
     *
     * @param itemView The type of item
     */
    public CalculatorViewHolder(final View itemView)
    {
        super(itemView);
        this.buttonClear = (Button) this.itemView.findViewById(R.id.bt_keyboard_clear);
        this.buttonBackspace = (ImageButton) this.itemView.findViewById(R.id.bt_keyboard_backspace);
        this.tvCalcView = (TextView) this.itemView.findViewById(R.id.tv_calc_visor);
        this.btClose = (ImageButton) this.itemView.findViewById(R.id.bt_close_keyboard);
        this.panelView = this.itemView.findViewById(R.id.calc_panel_button);
        this.tvPreviewValue = (TextView) this.itemView.findViewById(R.id.tv_preview_value);

        for(int id_button: buttons)
            this.itemView.findViewById(id_button).setOnClickListener(this);

        this.btClose.setOnClickListener(this);
    }

    private void treatVisible() {
        if (values.isClosed())
        {
            panelView.setVisibility(View.VISIBLE);
            btClose.setVisibility(View.VISIBLE);
            buttonBackspace.setImageResource(R.drawable.ic_backspace_black_24dp);
//                itemView.findViewById(R.id.separator).setVisibility(View.VISIBLE);
            values.setClosed(false);
        } else {
            panelView.setVisibility(View.GONE);
            btClose.setVisibility(View.GONE);
            buttonBackspace.setImageResource(R.drawable.ic_calculator_black);
//                itemView.findViewById(R.id.separator).setVisibility(View.GONE);
            values.setClosed(true);
        }
    }

    public void setValues(CalculatorDataSet values)
    {

    }

    @Override
    public boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position) {
        if(dataSet instanceof CalculatorDataSet)
        {
            this.values = (CalculatorDataSet) dataSet;
            this.tvCalcView.setText(values);
            this.treatVisible();
        }
        return true;
    }

    @Override
    public void onClick(View view)
    {

        int id = view.getId();
        boolean valid;
        switch (id)
        {
            case R.id.bt_keyboard_0:
            case R.id.bt_keyboard_1:
            case R.id.bt_keyboard_2:
            case R.id.bt_keyboard_3:
            case R.id.bt_keyboard_4:
            case R.id.bt_keyboard_5:
            case R.id.bt_keyboard_6:
            case R.id.bt_keyboard_7:
            case R.id.bt_keyboard_8:
            case R.id.bt_keyboard_9:
            case R.id.bt_keyboard_p:
                Button bt = (Button) view;
                valid = values.addChar(bt.getText().charAt(0));

                if(valid && onClickKeyboarListner != null)
                    onClickKeyboarListner.onClick(view, bt.getText().charAt(0), this.values);
                break;
            case R.id.bt_keyboard_backspace:
                if(!values.isClosed()) {
                    valid = values.removeChar();
                    if (valid && onClickKeyboarListner != null)
                        onClickKeyboarListner.onClickAction(view, SupportCalculator.KeyboardAction.BACKSPACE, this.values);
                }
                else onCloseAction();
                break;
            case R.id.bt_keyboard_clear:
                if(!values.isClosed()) {
                    valid = values.clear();
                    if (valid && onClickKeyboarListner != null)
                        onClickKeyboarListner.onClickAction(view, SupportCalculator.KeyboardAction.CLEAR, this.values);
                }
                break;
            case R.id.bt_close_keyboard:
                onCloseAction();
                break;
        }
        this.updateText();
    }

    private void onCloseAction() {
        if (values.isClosed())
        {
            panelView.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.ZoomInDown)
                    .duration(500)
                    .playOn(panelView)
            ;
            btClose.setVisibility(View.VISIBLE);
            buttonBackspace.setImageResource(R.drawable.ic_backspace_black_24dp);
            values.setClosed(false);
        } else {
            YoYo.with(Techniques.ZoomOutDown)
                    .duration(500)
                    .withListener(new SupportRecyclerAdapter.SupportAnimatorListener()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            panelView.setVisibility(View.GONE);
                        }
                    })
                    .playOn(panelView);
            btClose.setVisibility(View.GONE);
            buttonBackspace.setImageResource(R.drawable.ic_calculator_black);
            values.setClosed(true);
        }
        return;
    }

    private void updateText() {
        this.tvCalcView.setText(this.values);
    }

    public void valueChaged() {
        this.tvPreviewValue.setText(FMT.format(this.values.getPreviewPrice()));
    }

    public void setOnclickListiner(SupportCalculator.OnClickKeyboardListener onClickKeyboarListner)
    {
        this.onClickKeyboarListner = onClickKeyboarListner;
    }
}
