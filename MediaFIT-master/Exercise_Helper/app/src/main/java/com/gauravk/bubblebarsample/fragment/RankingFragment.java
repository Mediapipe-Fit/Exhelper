package com.gauravk.bubblebarsample.fragment;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gauravk.bubblebarsample.DB.Userdata.RetrofitAPI;
import androidx.appcompat.app.AppCompatActivity;

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
import com.gauravk.bubblebarsample.cfg.MyGlobal;

import org.w3c.dom.Text;

public class RankingFragment extends Fragment{

    private TextView scoretext;
    private TextView nicknametext;
    private ImageView profiletext;

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

        retrofitAPI.get_rank_data(MyGlobal.getInstance().getEmail()).enqueue(new Callback<rank>(){
            @Override
            public void onResponse(@NonNull Call<rank> call, @NonNull Response<rank> response){
                if(response.isSuccessful()) {
                    rank datalist = response.body();
                    int count = -1;
                    for (user1 obj : datalist.getData()){
                        count ++;
                        if (MyGlobal.getInstance().getProfile().equals(obj.getProfile())){
                            TextView myrank = getView().findViewById(R.id.myRank);
                            TextView mynickname = getView().findViewById(R.id.mynickname);
                            TextView myscore = getView().findViewById(R.id.myscore);
                            ImageView myImage = getView().findViewById(R.id.myfic);
                            myrank.setText(obj.getRanking() + ".");
                            mynickname.setText("닉네임: " + MyGlobal.getInstance().getNickname());
                            myscore.setText("점수: " + obj.getScore());
                            Glide.with(myImage).load(obj.getProfile()).circleCrop().into(myImage);
                        }
                        profiletext = getView().findViewById(R.id.fic01 + count);
                        Glide.with(profiletext).load(obj.getProfile()).circleCrop().into(profiletext);

                        scoretext = getView().findViewById(R.id.score01 + count);
                        scoretext.setText("점수: " + obj.getScore());
                        nicknametext = getView().findViewById(R.id.nickname01 + count);
                        nicknametext.setText("닉네임: " + obj.getName());
                    }
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