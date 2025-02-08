package com.xiaofeng.strangeloop.springframework;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    // 默认日期格式
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 获取当前时间的字符串，格式：yyyy-MM-dd HH:mm:ss
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(new Date());
    }

    // 将字符串转换为日期对象
    public static Date stringToDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format != null ? format : DEFAULT_DATE_FORMAT);
        return sdf.parse(dateStr);
    }

    // 将日期对象转换为字符串
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format != null ? format : DEFAULT_DATE_FORMAT);
        return sdf.format(date);
    }

    // 获取当前时间的时间戳
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    // 将时间戳转换为日期字符串
    public static String timestampToString(long timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format != null ? format : DEFAULT_DATE_FORMAT);
        return sdf.format(new Date(timestamp));
    }

    // 获取当前日期
    public static Date getCurrentDate() {
        return new Date();
    }

    // 获取当前日期字符串（格式：yyyy-MM-dd）
    public static String getCurrentDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    // 获取当前时间的毫秒数
    public static long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    // 获取不同时区的当前时间
    public static String getCurrentTimeInTimeZone(String timeZoneId) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    // 比较两个日期对象，返回-1, 0, 1（分别表示前者早、相同、后者早）
    public static int compareDates(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        return date1.compareTo(date2);
    }

    // 获取两个日期之间的天数差
    public static long getDaysDifference(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return diffInMillis / (1000 * 60 * 60 * 24);
    }

    // 获取两个日期之间的小时差
    public static long getHoursDifference(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return diffInMillis / (1000 * 60 * 60);
    }

    // 获取两个日期之间的分钟差
    public static long getMinutesDifference(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return diffInMillis / (1000 * 60);
    }

    // 获取两个日期之间的秒数差
    public static long getSecondsDifference(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return diffInMillis / 1000;
    }


}
