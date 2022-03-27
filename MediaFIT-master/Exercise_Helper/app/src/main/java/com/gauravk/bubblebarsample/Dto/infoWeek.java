package com.gauravk.bubblebarsample.Dto;

import android.util.Log;

import com.gauravk.bubblebarsample.cfg.Config;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class infoWeek {

    @SerializedName("status")
    private String message;
    @SerializedName("data")
    private info[] data;

    private HashMap<String,List<info>> DateInfo = new HashMap<>();

    public info[] getData() {
        return data;
    }
    public String getMessage() {return message;}

    public void setData(info[] data) {
        this.data = data;
    }

    public String toString() {
        return "ClassPojo [data = " + data + "]";
    }

    public void setDateInfo(){
        for(int i=1;i<=7;++i){
            List<info> temp = new ArrayList<>();
            Log.d("Test",Config.weekDate.get(Config.hangleDate[i]));
            this.DateInfo.put(Config.weekDate.get(Config.hangleDate[i]),temp);
        }
        for(int i=0; i< this.data.length; ++i){
            Log.d("Test",data[i].getDate());
            this.DateInfo.get(data[i].getDate()).add(data[i]);
        }
        for(int i=1;i<=7;++i){
            Log.d("Test",Config.weekDate.get(Config.hangleDate[i]));
            Log.d("Test2",this.DateInfo.get(Config.weekDate.get(Config.hangleDate[i])).toString());
        }

    }
    public List<info> getDateInfo(String date){
        return this.DateInfo.get(date);
    }

}
