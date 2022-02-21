package com.edu.ustb.utils;

import java.util.ArrayList;
import java.util.List;

public class TimeUtils {
    private String year;
    private String monthDay;
    private String hour;

    /**
     * 经理查询某人在某月时返回该月的截止日期
     *
     * @param time_start
     * @return
     */
    public static String getEndOfMonth(String time_start) {
        if (time_start.length() < 18) return null;
        String time_end = null;
        String temp = "0";
        StringBuilder strTemp = new StringBuilder(time_start);
        strTemp.setCharAt(9, '1');

        //得到当前月份
        int month = Integer.parseInt(time_start.substring(5, 7));
        month++;
        if (month < 10) {
            temp = "0";
            temp += String.valueOf(month);
            strTemp.replace(5, 7, temp);
            time_end = strTemp.toString();
        } else if (month >= 10 && month <= 12) {
            temp = String.valueOf(month);
            strTemp.replace(5, 7, temp);
            time_end = strTemp.toString();
        } else {
            strTemp.replace(8, strTemp.length(), "31 23:59:00");
            time_end = strTemp.toString();
        }
        return time_end;
    }

    public static String getStartOfDay(String time) {
        String sstart = " 00:00:01";
        String ddate = time.substring(0, time.indexOf(" "));
        return ddate + sstart;
    }

    public static String getEndOfDay(String time) {
        String eend = " 23:59:59";
        String ddate = time.substring(0, time.indexOf(" "));
        return ddate + eend;
    }

    /**
     * 获取"yyyy-mm-dd"～"yyyy-mm-dd"中的所有日子
     *
     * @param dstartDay
     * @param dendDay
     * @return "yyyy-mm-dd"字符串组成的list
     */
    public static List<String> getDaysBetween(String dstartDay, String dendDay) {
        List<String> daysBetween = new ArrayList<>();
        // yyyy-mm-dd
        //先单独抽取出年月
        String yymm = dstartDay.substring(0, dstartDay.lastIndexOf('-') + 1);
        //把日子抽出来，用int形式接收方便加运算
        int startDay = Integer.valueOf(dstartDay.substring(dstartDay.length() - 2));
        int endDay = Integer.valueOf(dendDay.substring(dendDay.length() - 2));
        String currentDay;
        for (int i = startDay; i <= endDay; i++) {
            //重新拼接年月，放到list中
            if (i < 10) {
                currentDay = "0" + i;
            } else {
                currentDay = String.valueOf(i);
            }
            String yymmdd = yymm + currentDay;
            daysBetween.add(yymmdd);
        }
        return daysBetween;
    }

    public static String getRealTimeByMonthDay(String date, String day) {
        return "2020-" + date + " " + day + ":00";
    }
}
