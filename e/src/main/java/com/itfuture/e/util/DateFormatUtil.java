package com.itfuture.e.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**日期格式化
 * @Description:
 * @Author 王小虎
 * @Version 1.0
 * @Date Created in 2021-12-24 15:51
 */
public class DateFormatUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date){
        return format.format(date);
    }

    public static long toTime(String formatString){
        try {
            return format.parse(formatString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
//    public static void main(String[] args) {
//        Date date = new Date();
//        String format = DateFormatUtil.format.format(date);
//        System.out.println(format);
//    }
}
