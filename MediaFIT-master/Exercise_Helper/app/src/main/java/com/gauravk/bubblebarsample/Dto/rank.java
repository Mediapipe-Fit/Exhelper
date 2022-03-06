package com.gauravk.bubblebarsample.Dto;

import com.gauravk.bubblebarsample.Dto.userRank;
import com.google.gson.annotations.SerializedName;

public class rank {
    @SerializedName("data")
    private userRank[] data;

    public userRank[] getData() {
        return data;
    }

    public void setData(userRank[] data) {
        this.data = data;
    }

    public String toString() {
        return "ClassPojo [data = " + data + "]";
    }
}