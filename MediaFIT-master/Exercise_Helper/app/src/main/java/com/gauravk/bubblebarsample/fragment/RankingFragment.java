package com.gauravk.bubblebarsample.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gauravk.bubblebarsample.Dto.RetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gauravk.bubblebarsample.Dto.userRank;
import com.gauravk.bubblebarsample.Dto.rank;
import com.gauravk.bubblebarsample.R;
import com.gauravk.bubblebarsample.cfg.MyGlobal;
import com.gauravk.bubblebarsample.cfg.userConfig;

public class RankingFragment extends Fragment{

    private TextView scoreText;
    private TextView nicknameText;
    private ImageView profileText;

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
        Log.d("Ranking", " RankingPage START");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.exhelper.site/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Log.d("Ranking", " Ranking Data 통신 시작");
        retrofitAPI.get_rank_data(userConfig.getInstance().getEmail()).enqueue(new Callback<rank>(){
            @Override
            public void onResponse(@NonNull Call<rank> call, @NonNull Response<rank> response){
                if(response.isSuccessful()) {
                    Log.d("Ranking", " Ranking Data 통신 성공");
                    rank datalist = response.body();
                    int count = -1;
                    for (userRank obj : datalist.getData()){
                        count ++;
                        if (userConfig.getInstance().getProfile().equals(obj.getProfile())){
                            TextView myrank = getView().findViewById(R.id.myRank);
                            TextView mynickname = getView().findViewById(R.id.mynickname);
                            TextView myscore = getView().findViewById(R.id.myscore);
                            ImageView myImage = getView().findViewById(R.id.myfic);
                            myrank.setText(obj.getRanking() + ".");
                            mynickname.setText("닉네임: " + userConfig.getInstance().getNickname());
                            myscore.setText("점수: " + obj.getScore());
                            Glide.with(myImage).load(obj.getProfile()).circleCrop().into(myImage);
                        }
                        profileText = getView().findViewById(R.id.fic01 + count);
                        Glide.with(profileText).load(obj.getProfile()).circleCrop().into(profileText);

                        scoreText = getView().findViewById(R.id.score01 + count);
                        scoreText.setText("점수: " + obj.getScore());
                        nicknameText = getView().findViewById(R.id.nickname01 + count);
                        nicknameText.setText("닉네임: " + obj.getName());
                    }
                }
            }
            @Override
            public void onFailure(Call<rank> call, Throwable t){
                Log.d("Ranking", " Ranking Data 통신 실패");
                t.printStackTrace();
            }
        });
        //Log.d("Ranking", " Ranking Data 통신 종료");
    }

}