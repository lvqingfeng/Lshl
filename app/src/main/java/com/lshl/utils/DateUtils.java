package com.lshl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/4.
 */
public class DateUtils {

    private static SimpleDateFormat sf = null;

    /**
     * 获取系统时间 格式为："yyyy/MM/dd "
     */
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }
    public static String getDateToStringMh(long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("MM-dd");
        return sf.format(d);
    }
    public static String getDateToStringText(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }
    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString2(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString3(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日\u3000HH:mm");
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToDate(String time) {
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getTimestampString(long date) {
        return com.hyphenate.util.DateUtils.getTimestampString(new Date(date));
    }

    public static String getAgeBirthDate(String strBirthDate) {

        if ("".equals(strBirthDate) || strBirthDate == null) {
            return "";
        }
        Date format = new Date();
        try {
            format = new SimpleDateFormat("yyyy年MM月dd日").parse(strBirthDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 读取当前日期
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);

        c.setTime(format);

        int yearBirthDate = c.get(Calendar.YEAR);
        int monthBirthDate = c.get(Calendar.MONTH) + 1;
        int dayBirthDate = c.get(Calendar.DATE);

        // 计算年龄
        int age = year - yearBirthDate - 1;
        if (monthBirthDate < month) {
            age++;
        } else if (monthBirthDate == month && dayBirthDate <= day) {
            age++;
        }
        return String.valueOf(age);
    }

}
