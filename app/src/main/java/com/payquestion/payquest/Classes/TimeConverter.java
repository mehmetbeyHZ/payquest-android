package com.payquestion.payquest.Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TimeConverter {
    String month, dayName;
    String hour;
    int day;
    public TimeConverter(String myDate) {
        try{
            SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", new Locale("tr"));
            format.parse(myDate);
            day = format.getCalendar().get(Calendar.DATE);
            dayName = (days()).get(format.getCalendar().get((Calendar.DAY_OF_WEEK)) - 1);
            month = (months()).get(format.getCalendar().get(Calendar.MONTH));
            hour = format.getCalendar().get(Calendar.HOUR_OF_DAY) + ":" + format.getCalendar().get(Calendar.MINUTE);
        }catch (Exception e)
        {
            month = "";
            dayName = "";
            day = 0;
            hour = "";
        }
    }

    public String getHour()
    {
        return hour;
    }

    public int getDay()
    {
        return day;
    }

    public String getDayName()
    {
        return dayName;
    }

    public String getMonth()
    {
        return month;
    }


    public static ArrayList<String> months() {
        ArrayList<String> myList = new ArrayList<>();
        myList.add("Ocak");
        myList.add("Şubat");
        myList.add("Mart");
        myList.add("Nisan");
        myList.add("Mayıs");
        myList.add("Haziran");
        myList.add("Temmuz");
        myList.add("Ağustos");
        myList.add("Eylül");
        myList.add("Ekim");
        myList.add("Kasım");
        myList.add("Aralık");
        return myList;
    }

    public static ArrayList<String> days() {
        ArrayList<String> myList = new ArrayList<>();
        myList.add("Pazar");
        myList.add("Pazartesi");
        myList.add("Salı");
        myList.add("Çarşamba");
        myList.add("Perşembe");
        myList.add("Cuma");
        myList.add("Cumartesi");
        return myList;
    }






}
