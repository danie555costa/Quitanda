package st.domain.quitanda.client.util;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Daniel Costa at 9/3/16.
 * Using user computer xdata
 */
public class FormatterFactory {

    public NumberFormat instanceFormatterQuantity()
    {
        NumberFormat fmt = NumberFormat.getInstance(new Locale("PT", "st"));
        fmt.setMaximumFractionDigits(1);
        fmt.setMinimumFractionDigits(1);
        fmt.setMaximumIntegerDigits(15);
        fmt.setMinimumIntegerDigits(1);
        fmt.setCurrency(Currency.getInstance("STD"));
        fmt.setRoundingMode(RoundingMode.FLOOR);
        return fmt;
    }

    public NumberFormat instanceFormatterMoney()
    {
        NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("pt", "ST"));
        fmt.setMaximumFractionDigits(2);
        fmt.setMinimumFractionDigits(2);
        fmt.setMinimumIntegerDigits(1);
        fmt.setCurrency(Currency.getInstance("STD"));
        return fmt;
    }
}
