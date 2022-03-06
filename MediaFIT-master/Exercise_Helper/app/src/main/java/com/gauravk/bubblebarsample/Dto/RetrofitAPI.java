package com.gauravk.bubblebarsample.Dto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @GET("rank/{nickname}")
    Call<rank> get_rank_data(@Path("nickname") String nickname);


    /*
    //유저 목록 가져오는 함수
    retrofitAPI.get_All_data().enqueue(new Callback<dataall>(){
        @Override
        public void onResponse(@NonNull Call<dataall> call, @NonNull Response<dataall> response){
            if(response.isSuccessful()) {
                dataall datalist = response.body();
                Log.d("TEST", "GOOD)");
            }
        }
        @Override
        public void onFailure(Call<dataall> call, Throwable t){
            Log.d("TEST" , "NOT GOOD");
            t.printStackTrace();
        }
    });
    */

    @POST("/user")
    Call<post_response> CreateAndUpdateUser(@Body user user);
    /*
    보내는 함수
    postuser에 다 넣고 보내면 댐

    user postuser = new user();
        postuser.setEmail("it is work?");
        postuser.setName("fwadl");
        postuser.setProfile(50);
        postuser.setSex(1);
        postuser.setAge("50~60");


        retrofitAPI.dp(postuser).enqueue(new Callback<post_response>() {
            @Override
            public void onResponse(Call<post_response> call, Response<post_response> response){
     //           Log.d("test",response.message());
                if(response.isSuccessful()){
                    Log.d("test","GOOD!");
                }
                else{
                    Log.d("test","NOT GOOD");
                }
            }
            public void onFailure(Call<post_response> call, Throwable t){
                Log.d("test","개짜증");
            }
        });
     */

}

