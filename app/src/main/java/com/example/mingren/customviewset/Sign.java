package com.example.mingren.customviewset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by MingRen on 2016/9/24.
 */
public class Sign {

    public static List<String> list = new ArrayList<>();

    public static void main(String arg[]) {
        Calendar c = Calendar.getInstance();
        int year;
        int week;
        c.setTimeInMillis(1483236610000l);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        year = c.get(Calendar.YEAR);
        week = c.get(Calendar.WEEK_OF_YEAR);
        System.out.println(year + "-" + week);
    }


    private String getWeekKey(boolean isThisWeek) {
        Calendar c = Calendar.getInstance();
        int year;
        int week;
        if (!isThisWeek) {
            c.setTimeInMillis(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7l);//注意减去的是long类型，不然会出错
        }
        year = c.get(Calendar.YEAR);
        week = c.get(Calendar.WEEK_OF_YEAR);
        return year + "-" + week;
    }
}
