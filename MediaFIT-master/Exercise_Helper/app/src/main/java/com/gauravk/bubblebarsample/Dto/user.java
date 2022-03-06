package com.gauravk.bubblebarsample.Dto;

import com.google.gson.annotations.SerializedName;

public class user {
    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("sex")
    private int sex;

    @SerializedName("age")
    private String age;

    @SerializedName("profile")
    private String profile;

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getSex(){
        return sex;
    }
    public void setSex(int sex){
        this.sex = sex;
    }
    public String getAge(){
        return age;
    }
    public void setAge(String age){
        this.age = age;
    }
    public String getProfile(){
        return profile;
    }
    public void setProfile(String profile){
        this.profile = profile;
    }
    /*
    public List<User> getData(){
        return this.data;
    }
    public void setData(List<User> data){
        this.data = data;
    }
    */
    @Override
    public String toString()
    {
        return "[Email = "+email+", sex = "+sex+", name = "+name+", age = "+age+", profile = "+profile+"]";
    }

}
