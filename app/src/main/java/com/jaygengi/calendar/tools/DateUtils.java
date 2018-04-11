package com.jaygengi.calendar.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * author: JayGengi 60167
 * email:  gengy@chinaraising.com
 * time:  2017/11/14 14:54
 */
public class DateUtils {

    /**
     * 通过年份和月份 得到当月的日子
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
                    return 29;
                }else{
                    return 28;
                }
            default:
                return  -1;
        }
    }

    /**
     * 返回当前月份1号位于周几
     * @param year
     * 		年份
     * @param month
     * 		月份，传入系统获取的，不需要正常的
     * @return
     * 	日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    /**
     * 【转换日期】根据传入Date日期转换为Strin格式
     * @author gengy 2018-04-11
     * @param date 用户传入的Date格式日期
     * @param datePattern 转换格式(默认yyyy-MM-dd HH:mm:ss)
     * @return 返回转换后的字符串日期
     */
    public static final String getDateStr(Date date,String datePattern){
        SimpleDateFormat df;
            df = new SimpleDateFormat(datePattern);
        return df.format(date);
    }
}
