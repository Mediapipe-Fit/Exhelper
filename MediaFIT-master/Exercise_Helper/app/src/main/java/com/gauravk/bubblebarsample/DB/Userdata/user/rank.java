package com.gauravk.bubblebarsample.DB.Userdata.user;

import com.google.gson.annotations.SerializedName;

public class rank {
    @SerializedName("data")
    private user1[] data;


    public user1[] getData() {
        return data;
    }

    public void setData(user1[] data) {
        this.data = data;
    }

    public String toString() {
        return "ClassPojo [data = " + data + "]";
    }
}