package gm.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final Long DAY_SECOND = 24*60*60*1000L;

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    public static DateFormat getDateFormat()
    {
        DateFormat df = threadLocal.get();
        if(df==null){
            df = new SimpleDateFormat(DATE_FORMAT);
            threadLocal.set(df);
        }
        return df;
    }



    //获取今天凌晨零点
    public static Date getToday(){
        return getZero(System.currentTimeMillis());
    }

    public static Date getBefore(Date date,int field){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, -1);
        return calendar.getTime();
    }

    /**
     * 获取date日期之后的天数
     */
    public static Date getAfter(Date date, int field){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field,1);
        return calendar.getTime();
    }

    /**
     * 获取当前时间戳当天零点
     * @param timeMillis
     * @return
     */
    public static Date getZero(Long timeMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 获取星期一
     * @param date
     * @return
     */
    public static Date getMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取指定日期当月1号
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static String format(Date date){
       return  getDateFormat().format(date);
    }

    public static Date parse(String dateStr) throws ParseException {
        return  getDateFormat().parse(dateStr);
    }





}
