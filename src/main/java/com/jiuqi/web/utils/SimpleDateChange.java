package com.jiuqi.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author fugaotian
 * @Version 1.0
 */
public class SimpleDateChange {

    private SimpleDateChange() {

    }

    public static List<LocalDate> dateChange(String[] list) {
        List<LocalDate> localDates = new ArrayList<>();
        list[0] = list[0].replace("Z", " UTC");
        list[1] = list[1].replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date time1 = null;
        try {
            time1 = format.parse(list[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date time2 = null;
        try {
            time2 = format.parse(list[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDate result1 = LocalDate.parse(defaultFormat.format(time1));
        LocalDate result2 = LocalDate.parse(defaultFormat.format(time2));
        localDates.add(result1);
        localDates.add(result2);
        return localDates;
    }
}

