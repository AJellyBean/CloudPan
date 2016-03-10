package com.quickly.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间处理工具类
 *
 *Created by mjiu
 */
@SuppressLint("SimpleDateFormat")
public final class TimeUtil {
    static Calendar cal = Calendar.getInstance();

    // 获取当前时间可以用来给图片命名或者记录聊聊天和评论时间
    public static long getCurrentTime() {

        long time = System.currentTimeMillis();

        return time;
    }

    /**
     * 此方法用于格式化时间格式
     *
     * @param
     * @return
     */
    public static String formatTimeDiff(long date) {
        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());//将时间格式化成的格式
        Date day = new Date();//当前时间
        String mtime = st.format(day);//将时间格式化成为yyyy-MM-dd

        Date time = stringToDate(mtime);//将yyyy-MM-dd型的时间的字符串转变成Date类型
        long timelong = dateToLong(time);//当前日期的长型 yyyy-MM-dd 类型的时间戳

        long diff = 0;
        String str = "";
        diff = (date - timelong)/1000;//时间差


        if (diff > 0) {
            str = "今天   " + hhmmss(Long.valueOf(date));
        } else if (diff < 0 && diff >= -24 * 60 * 60) {
            str = "昨天   " + hhmmss(Long.valueOf(date));

        } else if (diff < -24 * 60 * 60 && diff >= -2 * 24 * 60 * 60) {
            str = "前天  " + hhmmss(Long.valueOf(date));
        } else if (diff <= -2 * 24 * 60 * 60) {
            str = longToTime(date);
        }
        return str;
    }

    public static String formatTimeYMD(long date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        Date day = new Date(date);
        return time.format(day);
    }

    /**
     * 此方法用于格式化时间格式
     *
     * @param
     * @return
     */
    public static String getTimeDiffHM(String date) {

        long mDate = Long.parseLong(date + "000");// 将String转化成Long-->如果传入的是 13位的时间戳就不用添加后面的“000”


        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());//将时间格式化成的格式
        Date day = new Date();//当前时间

        Date time = stringToDate(st.format(day));//将yyyy-MM-dd型的时间的字符串转变成Date类型

        long diff = 0;
        String str = "";
        diff = mDate - dateToLong(time);//时间差

        if (diff > 0) {
            str = "今天   " + hhmm(Long.valueOf(date));
        } else if (diff < 0 && diff >= -24 * 60 * 60) {
            str = "昨天   " + hhmm(Long.valueOf(date));

        } else if (diff < -24 * 60 * 60 * 1000 && diff >= -2 * 24 * 60 * 60) {
            str = "前天  " + hhmm(Long.valueOf(date));
        } else if (diff <= -2 * 24 * 60 * 60) {
            str = longToTime(mDate);
        }
        return str;
    }

    /**
     * @param date 这里需要传入的是13为的时间戳
     * @return 但会的是   yyyy-MM-dd 类型的时间字符串
     */
    public static String longToTime(Long date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date day = new Date(date);
        return time.format(day);

    }

    /**
     * 此方法用于获取  HH:mm:ss 格式的时间
     *
     * @param date 这里需要传入的是13为的时间戳
     * @return 返回的是  HH:mm:ss  类型的时间字符串
     */
    public static String hhmmss(Long date) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date day = new Date(date);
        return time.format(day);

    }

    /**
     * 此方法用于获取  HH:mm 格式的时间
     *
     * @param date 这里需要传入的是13为的时间戳
     * @return 返回的是  HH:mm  类型的时间字符串
     */
    public static String hhmm(Long date) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date day = new Date(date);
        return time.format(day);

    }

    /**
     * 此方法用于获取  HH:mm 格式的时间
     *
     * @return 返回的是  HH:mm  类型的时间
     */
    public static int mm() {
        SimpleDateFormat formatter = new SimpleDateFormat("mm", Locale.getDefault());
        Date day = new Date(getCurrentTime());
        String m= formatter.format(day);
        int minute;
        if (m.startsWith("0")) {
            minute = Integer.decode(m.substring(1, 2));
        } else {
            minute = Integer.decode(m);
        }
        return minute;
    }

    /**
     * 此方法用于获取  HH格式的时间
     *
     * @return 返回的是  HH 24小时制的小时时间类型
     */
    public static int HH() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 此方法用于获取传入时间的小时
     *
     * @return 返回的是  hh 类型的时间
     */
    public static int hh(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH", Locale.getDefault());
        Date day = new Date(time);
        String h = formatter.format(day);
        int hour;
        if (h.startsWith("0")) {
            hour = Integer.decode(h.substring(1, 2));
        } else {
            hour = Integer.decode(h);
        }
        return hour;
    }


    /**
     * 将date时间转换成long类型的时间戳
     *
     * @param date date 时间类型
     * @return 返回的是13为时间戳
     */
    public static Long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 将当前时间格式化为  yyyy-MM-dd  年-月-日，其目的适用于获取当日  凌晨 0点的时间戳
     *
     * @param dateString 13为的时间戳
     * @return 返回的是date时间
     */
    public static Date stringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将当前时间转换成  yyyy-MM-dd 类型
     *
     * @return
     */
    public static String getYMD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date day = new Date(getCurrentTime());
        return formatter.format(day) + " ";
    }

    public static Date getTime(String time) {

        String tim = getYMD() + time;
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date date = null;
        try {
            date = fmt.parse(tim);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取明天的这个（传入的时间）时间
     *
     * @param todayTime
     * @return
     */
    public static long getTomorrowTime(long todayTime) {
        long oneDay = 24 * 60 * 60 * 1000;
        return todayTime + oneDay;
    }


    public static String TormatTimeHMDHM(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm", Locale.getDefault());
        Date day = new Date(time);
        return formatter.format(day) + " ";
    }
}
