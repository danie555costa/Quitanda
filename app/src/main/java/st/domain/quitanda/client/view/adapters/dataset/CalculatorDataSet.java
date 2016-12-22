package st.domain.quitanda.client.view.adapters.dataset;

import android.support.annotation.NonNull;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.quitanda.client.R;
import st.domain.quitanda.client.util.BaseCharacter;
import st.domain.quitanda.client.util.FormatterFactory;

import java.text.NumberFormat;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class CalculatorDataSet extends BaseCharacter implements BaseRecyclerAdapter.ItemDataSet
{
    private final NumberFormat formatter;
    private String originalTest;
    private int maxLength;
    private boolean closed;
    private double previewPrice;

    public CalculatorDataSet(int maxLength, double lastQuantity)
    {
        this.originalTest = lastQuantity+"";
        this.maxLength = maxLength;

        this.formatter = new FormatterFactory().instanceFormatterQuantity();

        int intPart = (int) lastQuantity;
        double decimalPart = lastQuantity-intPart;
        if(decimalPart == 0)
            originalTest = intPart+"";
        else if(lastQuantity == 0)
            originalTest = "";

    }

    @Override @NonNull
    public String toString()
    {
        if(this.accept(this.originalTest) == null) return  this.formatter.format(0);
        Double d = Double.valueOf(this.originalTest);
        return this.formatter.format(d);
    }

    @Override
    public int getTypeView() {
        return R.layout.item_calculator;
    }

    /**
     * ADD character in text
     * @param c
     * @return
     */
    public boolean addChar(char c)
    {
        String textFormater = this.toString();
        String aux = originalTest + c;
        Double auxFomater = this.accept(aux);
        if( auxFomater != null
                && aux.length()<=this.maxLength)
        {
            if(aux.contains(".")
                    && aux.charAt(aux.length()-1) != '.'
                    && textFormater.equals(formatter.format(auxFomater))) return false;
            else
            {
                this.originalTest = aux;
                return true;
            }
        }
        return false;
    }

    private Double accept(String aux) {
        try
        {
            return Double.parseDouble(aux);
        }
        catch (NullPointerException | NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Clear the text in calculator
     * @return
     */
    public boolean clear()
    {
        if(this.originalTest.length() == 0) return false;
        this.originalTest = "";
        return true;
    }

    public  boolean removeChar()
    {
        if(this.originalTest.length() == 0) return false;
        remove();
        if(originalTest.length()>0
                && originalTest.charAt(originalTest.length()-1) == '.')
            remove();
        return true;
    }

    private void remove() {
        this.originalTest = this.originalTest.substring(0, this.originalTest.length()-1);
    }

    public double getValue()
    {
        Double value = accept(this.originalTest);
        if(value != null) return  value;
        return 0.0;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setPreviewPrice(double previewPrice) {
        this.previewPrice = previewPrice;
    }

    public double getPreviewPrice() {
        return previewPrice;
    }
}
