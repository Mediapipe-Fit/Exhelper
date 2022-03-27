package com.gauravk.bubblebarsample.cfg;

import com.gauravk.bubblebarsample.Dto.GetInfoListener;
import com.gauravk.bubblebarsample.Dto.infoWeek;

public class userConfig {
    private static userConfig instance = null;
    public static synchronized userConfig getInstance(){
        if (null == instance){
            instance = new userConfig();
        }
        return instance;
    }
    private String nickname;
    private String profile;
    private String email;
    private String str_gender;
    private String age_range;
    private String birthday;
    private Integer gender;
    private infoWeek weekData = null;
    private GetInfoListener HomeInfoListener;
    private GetInfoListener RoutineInfoListener;

    public String getEmail() { return this.email; }
    public String getProfile() { return this.profile; }
    public String getNickname() { return this.nickname; }
    public int getGender() { return this.gender; }
    public String getAge_range() { return this.age_range; }
    public String getBirthday() { return this.birthday; }
    public infoWeek getWeekData() {return this.weekData;}
    public GetInfoListener getRoutineInfoListener(){return this.RoutineInfoListener;}

    public void setEmail(String email){this.email = email;}
    public void setGender(String gender){
        this.str_gender = gender;
        if(gender == "MALE"){
            this.gender = 0;
        }
        else {
            this.gender = 1;
        }
    }
    public void setBirthday(String birthday){this.birthday = birthday;}
    public void setAge_range(String age_range){this.age_range = age_range;}
    public void setNickname(String nickname){this.nickname = nickname;}
    public void setProfile(String profile){this.profile = profile;}
    public void setWeekData(infoWeek weekData){this.weekData = weekData;}
    public void setHomeInfoListener(GetInfoListener HomeInfoListener){this.HomeInfoListener = HomeInfoListener;}
    public void setRoutineInfoListener(GetInfoListener RoutineInfoListener){this.RoutineInfoListener = RoutineInfoListener;}
}
