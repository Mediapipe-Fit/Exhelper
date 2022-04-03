package com.gauravk.bubblebarsample.Dto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<CUD_Response> CreateAndUpdateUser(@Body user user);

    @POST("/info")
    Call<CUD_Response> CreateInfo(@Body info Info);

    @GET("/info/{email}")
    Call<infoWeek> GetInfo(@Path("email") String email,@Query("date") String day);

    @PUT("/info/{email}")
    Call<CUD_Response> UpdateInfo(@Path("email") String email, @Query("date") String day,@Query("exername") String Exername, @Query("sequence") int Sequence, @Body info Info);


    @DELETE("/info/{email}")
    Call<CUD_Response> DeleteInfo(@Path("email") String email,@Query("date") String day,@Query("exername") String Exername,@Query("sequence") int Sequence);

    /*
    보내는 함수
    postuser에 다 넣고 보내면 댐

    user postuser = new user();
        postuser.setEmail("it is work?");
        postuser.setName("fwadl");
        postuser.setProfile(50);
        postuser.setSex(1);
        postuser.setAge("50~60");


        retrofitAPI.dp(postuser).enqueue(new Callback<CUD_Response>() {
            @Override
            public void onResponse(Call<CUD_Response> call, Response<CUD_Response> response){
     //           Log.d("test",response.message());
                if(response.isSuccessful()){
                    Log.d("test","GOOD!");
                }
                else{
                    Log.d("test","NOT GOOD");
                }
            }
            public void onFailure(Call<CUD_Response> call, Throwable t){
                Log.d("test","개짜증");
            }
        });
     */

}

