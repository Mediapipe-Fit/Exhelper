package com.gauravk.bubblebarsample.Dto;

import android.util.Log;

import androidx.annotation.StringDef;

import com.gauravk.bubblebarsample.cfg.Config;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
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
        /*
        for(int i=1;i<=7;++i){
            Log.d("Test",Config.weekDate.get(Config.hangleDate[i]));
            Log.d("Test2",this.DateInfo.get(Config.weekDate.get(Config.hangleDate[i])).toString());
        }*/

    }
    public List<String> getSequence(String date){
        List<String> Sequence = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9","10"));
        for(int i=0;i<this.DateInfo.get(date).size();++i){
            Sequence.remove(String.valueOf(this.DateInfo.get(date).get(i).getSequence()));
        }
        return Sequence;
    }
    public List<String> getMySequence(String date,int mySequence){
        List<String> Sequence = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9","10"));
        for(int i=0;i<this.DateInfo.get(date).size();++i){
            int cur = this.DateInfo.get(date).get(i).getSequence();
            if(mySequence != cur)   Sequence.remove(String.valueOf(cur));
        }
        return Sequence;
    }

    public int getInsertedPosition(String date, int sequence){
        int start = 0;
        int end = this.DateInfo.get(date).size();
        for(;start<end;++start){
            if(this.DateInfo.get(date).get(start).getSequence() > sequence){
                break;
            }
        }
        //Log.d("sequence",String.valueOf(sequence));
        //Log.d("position",String.valueOf(start));
        return start;
    }

    public int getPosition(String date, int sequence){
        int start = 0;
        int end = this.DateInfo.get(date).size();
        for(;start<end;++start){
            Log.d("sequence",String.valueOf(sequence) +", "+ String.valueOf(this.DateInfo.get(date).get(start).getSequence()));
            Log.d("position",String.valueOf(start));
            if(this.DateInfo.get(date).get(start).getSequence() == sequence){
                break;
            }
        }
        return start;
    }

    public List<info> getDateInfoList(String date){
        return this.DateInfo.get(date);
    }


    public info getDateInfo(String date, int position){
        Log.d("what>","size : " + this.DateInfo.get(date).size()+ ", position : " + position);
        return this.DateInfo.get(date).get(position);
    }
    public void setDateInfo(String date, int position, info temp){
        this.DateInfo.get(date).set(position,temp);
    }

}
