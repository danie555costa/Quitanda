package com.st.ggviario.client.util.components;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Daniel Costa at 8/27/16.
 * Using user computer xdata
 */
public class DatePickerControl implements DatePickerDialog.OnDateSetListener
{
    public DatePickerControl(String label)
    {
        this.calendar = Calendar.getInstance();
        this.label = label;
    }

    public  void DatePickerDialog(String label, int year, int monthOfYear, int dayOfMonth)
    {
        this.label = label;
        this.calendar = Calendar.getInstance();
        this.calendar.set(Calendar.YEAR, year);
        this.calendar.set(Calendar.MONTH, monthOfYear);
        this.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
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
    }

    public String getLabel() {
        return label;
    }
}
