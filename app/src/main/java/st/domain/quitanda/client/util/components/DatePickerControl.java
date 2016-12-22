package st.domain.quitanda.client.util.components;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Costa at 8/27/16.
 * Using user computer xdata
 */
public class DatePickerControl implements DatePickerDialog.OnDateSetListener
{

    private List<OnChangeCalendar> listOnChangeCalendar;

    public DatePickerControl(String label)
    {
        this.calendar = Calendar.getInstance();
        this.label = label;
        this.listOnChangeCalendar = new ArrayList<>();
    }

    public  void DatePickerDialog(String label, int year, int monthOfYear, int dayOfMonth)
    {
        this.label = label;
        this.calendar = Calendar.getInstance();
        this.calendar.set(Calendar.YEAR, year);
        this.calendar.set(Calendar.MONTH, monthOfYear);
        this.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    public void addOnChangeCalendar(OnChangeCalendar onChangeCalendar)
    {
        this.listOnChangeCalendar.add(onChangeCalendar);
    }

    private Calendar calendar;
    private String label;


    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getMonth()
    {
        return calendar.get(Calendar.MONTH);
    }

    public int getDay ()
    {
        return  calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        this.calendar.set(Calendar.YEAR, year);
        this.calendar.set(Calendar.MONTH, monthOfYear);
        this.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        for(OnChangeCalendar onChageCalendar: this.listOnChangeCalendar)
            onChageCalendar.accept(this);
    }

    public String getLabel() {
        return label;
    }

    public String getData(String formatter) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(formatter);
        Date date = this.calendar.getTime();
        return dateFormatter.format(date);
    }
}
