package com.gauravk.bubblebarsample.cfg;

import android.util.Log;

import com.gauravk.bubblebarsample.Dto.RetrofitAPI;
import com.gauravk.bubblebarsample.Dto.post_response;
import com.gauravk.bubblebarsample.Dto.user;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitObject {
    private static RetrofitObject instance = null;
    public static synchronized RetrofitObject getInstance(){
        if(instance == null){
            instance = new RetrofitObject();
        }
        return instance;
    }


    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.exhelper.site/")
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
                    Log.d("User",response.body().getMessage());
                }
                else{
                    Log.d("User","CreateOrUpdate 통신 오류 : " + response.body().getMessage());
                }
            }
            public void onFailure(Call<post_response> call, Throwable t){
                Log.d("User","CreateOrUpdate 통신 실패");
            }
        });
    }
}
