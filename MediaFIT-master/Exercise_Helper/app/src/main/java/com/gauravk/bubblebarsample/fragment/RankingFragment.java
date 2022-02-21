package com.gauravk.bubblebarsample.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gauravk.bubblebarsample.DB.Userdata.RetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gauravk.bubblebarsample.DB.Userdata.user.user1;
import com.gauravk.bubblebarsample.DB.Userdata.user.dataall;
import com.gauravk.bubblebarsample.DB.Userdata.user.rank;
import com.gauravk.bubblebarsample.R;

public class RankingFragment extends Fragment {
    public static RankingFragment newInstance() {
        RankingFragment fragment = new RankingFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.exhelper.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        retrofitAPI.get_rank_data().enqueue(new Callback<rank>(){
            @Override
            public void onResponse(@NonNull Call<rank> call, @NonNull Response<rank> response){
                if(response.isSuccessful()) {
                    rank datalist = response.body();
                    user1 temp = datalist.getData()[0];

                    Log.d("GOOOOD", temp.getEmail());
                }
            }
            @Override
            public void onFailure(Call<rank> call, Throwable t){
                Log.d("TEST" , "실패실패");
                t.printStackTrace();
            }
        });

    }

}