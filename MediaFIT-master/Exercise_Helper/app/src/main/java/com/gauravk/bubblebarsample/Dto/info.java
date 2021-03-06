package com.gauravk.bubblebarsample.Dto;

import com.google.gson.annotations.SerializedName;

public class info implements Comparable<info>{
    //기본생성자
    public info(){}
    // 전체 생성자
    public info(String date, String email,String exername, int sequence, int setNum, int repeatNum,int restTime, int setComplete, int current){
        this.date = date;
        this.email = email;
        this.exername = exername;
        this.sequence = sequence;
        this.setNum = setNum;
        this.repeatNum = repeatNum;
        this.restTime = restTime;
        this.setComplete = setComplete;
        this.current = current;
    }
    @SerializedName("date")
    private String date;

    @SerializedName("email")
    private String email;

    @SerializedName("exername")
    private String exername;

    @SerializedName("sequence")
    private int sequence;

    @SerializedName("setNum")
    private int setNum;

    @SerializedName("repeatNum")
    private int repeatNum;

    @SerializedName("restTime")
    private int restTime;

    @SerializedName("setComplete")
    private int setComplete;

    @SerializedName("current")
    private int current;



    public void setDate(String date){this.date = date;}
    public void setEmail(String email){this.email = email;}
    public void setExername(String exername){this.exername = exername;}
    public void setSequence(int sequence){this.sequence = sequence;}
    public void setSetNum(int setNum){this.setNum = setNum;}
    public void setRepeatNum(int repeatNum){this.repeatNum = repeatNum;}
    public void setRestTime(int restTime){this.restTime = restTime;}
    public void setSetComplete(int setComplete){this.setComplete = setComplete;}
    public void setCurrent(int current){this.current = current;}

    public String getDate(){return this.date;}
    public String getEmail(){return this.email;}
    public String getExername(){return this.exername;}
    public int getSequence(){return this.sequence;}
    public int getSetNum(){return this.setNum;}
    public int getRepeatNum(){return this.repeatNum;}
    public int getRestTime(){return this.restTime;}
    public int getSetComplete(){return this.setComplete;}
    public int getCurrent(){return this.current;}


    @Override
    public String toString()
    {
        return "[date = "+date+", email = "+email+", exername = "+exername+", sequence = "+sequence+", setNum = "+setNum+", repeatNum = "+repeatNum+", restTime = "+restTime+", setComplete = "+setComplete+", current = "+current+"]";
    }

    @Override
    public int compareTo(info o) {
        if(this.sequence < o.sequence){
            return -1;
        }else{
            return 1;
        }
    }
}
