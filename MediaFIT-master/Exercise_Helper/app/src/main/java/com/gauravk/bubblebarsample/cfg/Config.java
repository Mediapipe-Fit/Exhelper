package com.gauravk.bubblebarsample.cfg;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Config {

    public static final String DATABASE_NAME = "Routine-db";

    //column names of Routine table
    public static final String TABLE_Routine = "Routines";
    public static final String COLUMN_Routine_ID = "_id";
    public static final String COLUMN_Weekday = "Weekday";
    public static final String COLUMN_RegNO = "RegNO";
    public static final String COLUMN_Exercise_NAME = "name";
    public static final String COLUMN_Routine_Set_num = "Set_num";
    public static final String COLUMN_Routine_Repeat_num = "Repeat_num";
    public static final String COLUMN_Routine_Rest_time = "Rest_time";
    public static final String COLUMN_Routine_Counts = "Counts";
    public static final String COLUMN_Routine_Complete = "Complete";

    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_Routine = "create_Routine";
    public static final String UPDATE_Routine = "update_Routine";

    public static String selected_weekday = "";
    public static long selected_ID = -1;
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    public static Calendar calendar = Calendar.getInstance();
    public static HashMap<String,String> weekDate = new HashMap<>();
    public static String[] hangleDate = new String[]{"없음","일","월","화","수","목","금","토"};

    public static void setToday(){
        calendar.setTime(new Date());
    }

    public static void setWeek(){
        Calendar tmpcal = calendar;
        int temp_index = index;
        int cnt = 0;
        while(true){
            if(temp_index == 8){
                temp_index = 1;
            }
            weekDate.put(hangleDate[temp_index],yyyyMMdd.format(tmpcal.getTime()));
            Log.d("week",weekDate.get(hangleDate[temp_index]));
            tmpcal.add(Calendar.DATE,1);
            cnt++;
            temp_index++;
            if(cnt == 7) break;
        }
    }



    public static int index = -1;
    public static String today_hangle(){
        index = calendar.get(Calendar.DAY_OF_WEEK);
        return hangleDate[index];
    }
}