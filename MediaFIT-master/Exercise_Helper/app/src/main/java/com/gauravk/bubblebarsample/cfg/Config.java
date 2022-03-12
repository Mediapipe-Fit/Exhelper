package com.gauravk.bubblebarsample.cfg;

import java.util.Calendar;

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
    public static String Today = "";

    public static int index = -1;
    public static String today_hangle(){
        Calendar cal = Calendar.getInstance();
        index = cal.get(Calendar.DAY_OF_WEEK);
        String WhatWeek;
        switch (index){
            case 1:
                WhatWeek = "일";
                break;
            case 2:
                WhatWeek = "월";
                break;
            case 3:
                WhatWeek = "화";
                break;
            case 4:
                WhatWeek = "수";
                break;
            case 5:
                WhatWeek = "목";
                break;
            case 6:
                WhatWeek = "금";
                break;
            case 7:
                WhatWeek = "토";
                break;
            default:
                WhatWeek = "이상하다";
                break;
        }
        return WhatWeek;
    }
}