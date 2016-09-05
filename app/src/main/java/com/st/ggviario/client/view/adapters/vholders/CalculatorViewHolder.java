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
import com.st.ggviario.client.util.FormatterFactory;
import com.st.ggviario.client.util.animator.Selectable;
import com.st.ggviario.client.view.adapters.dataset.CalculatorDataSet;

import java.text.NumberFormat;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class CalculatorViewHolder extends BaseRecyclerAdapter.ItemViewHolder implements View.OnClickListener
{
    private final NumberFormat formatterMoney;
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

    private final ImageButton buttonBackspace;
    private final TextView tvCalcView;
    private final ImageButton btClose;
    private final View panelView;
    private final TextView tvPreviewValue;
    private CalculatorDataSet values;
    private OnClickKeyboardListener onClickKeyboarListener;

    /**
     *
     * @param itemView The type of item
     */
    public CalculatorViewHolder(final View itemView)
    {
        super(itemView);
        this.buttonBackspace = (ImageButton) this.itemView.findViewById(R.id.bt_keyboard_backspace);
        this.tvCalcView = (TextView) this.itemView.findViewById(R.id.tv_calc_visor);
        this.btClose = (ImageButton) this.itemView.findViewById(R.id.bt_close_keyboard);
        this.panelView = this.itemView.findViewById(R.id.calc_panel_button);
        this.tvPreviewValue = (TextView) this.itemView.findViewById(R.id.tv_preview_value);

        FormatterFactory formatterfactory = new FormatterFactory();
        this.formatterMoney = formatterfactory.instanceFormatterMoney();
        for(int id_button: buttons)
            this.itemView.findViewById(id_button).setOnClickListener(this);

        this.btClose.setOnClickListener(this);
    }

    private void treatVisible()
    {
        if (values.isClosed())
        {
            panelView.setVisibility(View.VISIBLE);
            btClose.setVisibility(View.VISIBLE);
            buttonBackspace.setImageResource(R.drawable.ic_backspace_black_24dp);
            values.setClosed(false);
        } else {
            panelView.setVisibility(View.GONE);
            btClose.setVisibility(View.GONE);
            buttonBackspace.setImageResource(R.drawable.ic_calculator_black);
            values.setClosed(true);
        }
    }

    @Override
    public boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position) {
        if(dataSet instanceof CalculatorDataSet)
        {
            this.values = (CalculatorDataSet) dataSet;
            this.tvCalcView.setText(values);
            this.tvPreviewValue.setText(formatterMoney.format(values.getPreviewPrice()));
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
                if(valid && onClickKeyboarListener != null)
                    onClickKeyboarListener.onClickKeyboard(view, bt.getText().charAt(0), this);
                break;

            case R.id.bt_keyboard_backspace:
                if(!values.isClosed()) {
                    valid = values.removeChar();
                    if (valid && onClickKeyboarListener != null)
                        onClickKeyboarListener.onClickKeyboardAction(view, KeyboardAction.BACKSPACE, this);
                }
                else onCloseAction();
                break;

            case R.id.bt_keyboard_clear:
                if(!values.isClosed()) {
                    valid = values.clear();
                    if (valid && onClickKeyboarListener != null)
                        onClickKeyboarListener.onClickKeyboardAction(view, KeyboardAction.CLEAR, this);
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
        this.tvPreviewValue.setText(formatterMoney.format(this.values.getPreviewPrice()));
    }

    public void setOnKeyboardClickListener(OnClickKeyboardListener onClickKeyboarListener)
    {
        this.onClickKeyboarListener = onClickKeyboarListener;
    }

    public double getValue() {
        return this.values.getValue();
    }

    public void setPreviewPrice(double previewPrice) {
        if(values != null)
            this.values.setPreviewPrice(previewPrice);
        this.tvPreviewValue.setText(formatterMoney.format(previewPrice));
    }

    public interface OnClickKeyboardListener
    {
        void onClickKeyboard(View view, char key, CalculatorViewHolder data);

        void onClickKeyboardAction(View view, KeyboardAction action, CalculatorViewHolder data);
    }

    public enum KeyboardAction
    {
        BACKSPACE,
        CLEAR
    }
}
