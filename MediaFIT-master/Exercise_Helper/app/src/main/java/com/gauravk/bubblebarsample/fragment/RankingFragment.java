package com.gauravk.bubblebarsample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gauravk.bubblebarsample.DB.Userdata.user.RetrofitAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import androidx.fragment.app.Fragment;

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

    }

}