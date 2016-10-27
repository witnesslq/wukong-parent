package com.easemob.wukong;

import com.easemob.wukong.utils.date.DateUtils0;

import java.util.Date;

/**
 * Created by dongwentao on 16/10/26.
 */
public class GeneralTest {
    public static void main(String[] args) {
        System.out.println(new Date().getTime());
        System.out.println(DateUtils0.fromLong(1477456914L,DateUtils0.defaultPattern0));
    }
}
