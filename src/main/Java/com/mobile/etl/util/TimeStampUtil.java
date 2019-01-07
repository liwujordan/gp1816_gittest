package com.mobile.etl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampUtil {

      /*public static void  main(String[] args){
           System.out.println("Hello World");
      }*/
      public static String dealTimeStamp(String timetemp){
          String str[] = timetemp.split("\\.");
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String time = sdf.format(new Date(Long.parseLong(str[0])));
          return time;
      }
}
