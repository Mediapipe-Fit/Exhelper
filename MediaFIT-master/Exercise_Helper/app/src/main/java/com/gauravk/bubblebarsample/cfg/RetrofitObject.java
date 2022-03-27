package com.gauravk.bubblebarsample.cfg;

import android.util.Log;

import com.gauravk.bubblebarsample.Dto.GetInfoListener;
import com.gauravk.bubblebarsample.Dto.RetrofitAPI;
import com.gauravk.bubblebarsample.Dto.info;
import com.gauravk.bubblebarsample.Dto.infoWeek;
import com.gauravk.bubblebarsample.Dto.post_response;
import com.gauravk.bubblebarsample.Dto.user;

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


        retrofitAPI.CreateAndUpdateUser(postuser).enqueue(new Callback<post_response>() {
            @Override
            public void onResponse(Call<post_response> call, Response<post_response> response){
                //           Log.d("test",response.message());
                if(response.isSuccessful()){
                    Log.d("CUser",response.body().getMessage());
                }
                else{
                    Log.d("CUser","CreateOrUpdate 통신 오류 : " + response.body());
                }
            }
            public void onFailure(Call<post_response> call, Throwable t){
                Log.d("CUser","CreateOrUpdate 통신 실패");
            }
        });
    }

    public void CreateInfo(info curInfo){

        retrofitAPI.CreateInfo(curInfo).enqueue(new Callback<post_response>() {
            @Override
            public void onResponse(Call<post_response> call, Response<post_response> response){
                if(response.isSuccessful()){
                    Log.d("CInfo",response.body().getMessage());
                    GetInfo(userConfig.getInstance().getRoutineInfoListener());
                }
                else{
                    Log.d("CInfo","CreateInfo 통신 오류");
                }
            }
            public void onFailure(Call<post_response> call, Throwable t){
                Log.d("CInfo","CreateInfo 통신 실패");
            }
        });
    }
    public void GetInfo(GetInfoListener getInfoListener){
        retrofitAPI.GetInfo(userConfig.getInstance().getEmail(),Config.today_string()).enqueue(new Callback<infoWeek>() {
            @Override
            public void onResponse(Call<infoWeek> call, Response<infoWeek> response){
                if(response.isSuccessful()){
                    Log.d("GInfo","GetInfoListener 통신 성공");
                    userConfig.getInstance().setWeekData(response.body());
                    userConfig.getInstance().getWeekData().setDateInfo();
                    getInfoListener.onGetInfoSuccess();
                }
                else{
                    Log.d("GInfo","GetInfoListener 통신 오류");
                }
            }
            public void onFailure(Call<infoWeek> call, Throwable t){
                Log.d("GInfo","GetInfoListener 통신 실패");
            }
        });
    }

}
