package com.easemob.wukong.utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dongwentao on 16/10/26.
 */
public class DateUtils0 {

    public static String defaultPattern0 = "yyyy-MM-dd HH:mm:ss";

    public static Date fromLong(Long date){
        return new Date(date);
    }

    public static String fromLong(Long date,String pattern){
        return new SimpleDateFormat(pattern).format(new Date(date));
    }
}
