package com.hziee.jinli.utils;

import org.apache.tomcat.jni.Time;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 *
 * Date 工具类
 *
 */
public class DateUtil {
    public static int caculate_divde_date(Date day1, Date day2){
        return Math.abs((int)day1.getTime() / (60 * 60 * 1000 * 24)-(int)day2.getTime() / (24 * 60 * 60 * 1000));
    }

    public static Date caculate_int_add_date(Date day1, int time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day1);
        calendar.add(Calendar.DATE, time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println(sdf.format(day1.getTime() + time * 60 * 60 * 24 * 1000));
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }
}
