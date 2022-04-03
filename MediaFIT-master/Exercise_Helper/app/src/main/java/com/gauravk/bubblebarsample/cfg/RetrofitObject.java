package com.gauravk.bubblebarsample.cfg;

import android.util.Log;

import com.gauravk.bubblebarsample.Dto.CUD_Response;
import com.gauravk.bubblebarsample.Dto.InfoChangeListener;
import com.gauravk.bubblebarsample.Dto.RetrofitAPI;
import com.gauravk.bubblebarsample.Dto.info;
import com.gauravk.bubblebarsample.Dto.infoWeek;
import com.gauravk.bubblebarsample.Dto.user;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitObject{
    private static RetrofitObject instance = null;
    public static synchronized RetrofitObject getInstance(){
        if(instance == null){
            instance = new RetrofitObject();
        }
        return instance;
    }


    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.Domain)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    public void CreateAndUpdateUser(){
        user postuser = new user();
        postuser.setEmail(userConfig.getInstance().getEmail());
        postuser.setName(userConfig.getInstance().getNickname());
        postuser.setProfile(userConfig.getInstance().getProfile());
        postuser.setSex(userConfig.getInstance().getGender());
        postuser.setAge(userConfig.getInstance().getAge_range());


        retrofitAPI.CreateAndUpdateUser(postuser).enqueue(new Callback<CUD_Response>() {
            @Override
            public void onResponse(Call<CUD_Response> call, Response<CUD_Response> response){
                //           Log.d("test",response.message());
                if(response.isSuccessful()){
                    Log.d("CUser",response.body().getMessage());
                }
                else{
                    Log.d("CUser","CreateOrUpdate 통신 오류 : " + response.body());
                }
            }
            public void onFailure(Call<CUD_Response> call, Throwable t){
                Log.d("CUser","CreateOrUpdate 통신 실패");
            }
        });
    }

    public void CreateInfo(info curInfo,int position){

        retrofitAPI.CreateInfo(curInfo).enqueue(new Callback<CUD_Response>() {
            @Override
            public void onResponse(Call<CUD_Response> call, Response<CUD_Response> response){
                if(response.isSuccessful()){
                    Log.d("CInfo","CreateInfo 통신 성공");
                    //GetInfo(userConfig.getInstance().getRoutineInfoListener());
                    userConfig.getInstance().getRoutineInfoListener().onInfoCreated(position);
                }
                else{
                    Log.d("CInfo","CreateInfo 통신 오류");
                }
            }
            public void onFailure(Call<CUD_Response> call, Throwable t){
                Log.d("CInfo","CreateInfo 통신 실패");
            }
        });
    }
    public void GetInfo(InfoChangeListener infoChangeListener){
        retrofitAPI.GetInfo(userConfig.getInstance().getEmail(),Config.today_string()).enqueue(new Callback<infoWeek>() {
            @Override
            public void onResponse(Call<infoWeek> call, Response<infoWeek> response){
                if(response.isSuccessful()){
                    Log.d("GInfo","GetInfo 통신 성공");
                    userConfig.getInstance().setWeekData(response.body());
                    userConfig.getInstance().getWeekData().setDateInfo();
                    infoChangeListener.onInfoGetSuccesse();
                }
                else{
                    Log.d("GInfo","GetInfo 통신 오류");
                }
            }
            public void onFailure(Call<infoWeek> call, Throwable t){
                Log.d("GInfo","GetInfo 통신 실패");
            }
        });
    }
    public void DeleteInfo(info selected, int position){
        retrofitAPI.DeleteInfo(userConfig.getInstance().getEmail(),selected.getDate(),selected.getExername(),selected.getSequence()).enqueue(new Callback<CUD_Response>() {
            @Override
            public void onResponse(Call<CUD_Response> call, Response<CUD_Response> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        Log.d("DInfo", "DeleteInfo 통신 성공");
                        userConfig.getInstance().getWeekData().getDateInfoList(selected.getDate()).remove(position);
                        userConfig.getInstance().getRoutineInfoListener().onInfoDeleted(position);
                    } else {
                        Log.d("DInfo", "DeleteInfo 통신 오류");
                    }
                }
            }
            public void onFailure(Call<CUD_Response> call, Throwable t){
                    Log.d("DInfo","DeleteInfo 통신 실패");
            }
        });
    }

    public void UpdateInfo(String PreExerName, int PreSequence, info selected, int position){
        retrofitAPI.UpdateInfo(selected.getEmail(),selected.getDate(),PreExerName,PreSequence,selected).enqueue(new Callback<CUD_Response>() {
            @Override
            public void onResponse(Call<CUD_Response> call, Response<CUD_Response> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        Log.d("UInfo", "UpdateInfo 통신 성공");
                        Collections.sort(userConfig.getInstance().getWeekData().getDateInfoList(selected.getDate()));
                        userConfig.getInstance().getRoutineInfoListener().onInfoChanged(position);
                    } else {
                        Log.d("UInfo", "UpdateInfo 통신 오류");
                    }
                }
            }
            public void onFailure(Call<CUD_Response> call, Throwable t){
                Log.d("UInfo","UpdateInfo 통신 실패");
            }
        });
    }

}
