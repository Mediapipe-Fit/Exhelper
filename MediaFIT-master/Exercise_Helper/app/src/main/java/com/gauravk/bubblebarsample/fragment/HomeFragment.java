package com.gauravk.bubblebarsample.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.gauravk.bubblebarsample.DB.CreateRoutine.Routine;
import com.gauravk.bubblebarsample.DB.QueryClass;
import com.gauravk.bubblebarsample.DB.ShowRoutine.HomeViewAdapter;
import com.gauravk.bubblebarsample.Dto.InfoChangeListener;
import com.gauravk.bubblebarsample.Dto.info;
import com.gauravk.bubblebarsample.R;
import com.gauravk.bubblebarsample.cfg.Config;
import com.gauravk.bubblebarsample.cfg.RetrofitObject;
import com.gauravk.bubblebarsample.cfg.userConfig;
import com.gauravk.bubblebarsample.mlkit.mlpose.RoutineCameraXLivePreviewActivity;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment
        implements CircleProgressBar.ProgressFormatter, InfoChangeListener {

    private QueryClass databaseQueryClass;

    private List<info> Days_routineList = new ArrayList<>();

    private RecyclerView recyclerView;
    private HomeViewAdapter routineListRecyclerViewAdapter;
    private static final String DEFAULT_PATTERN = "%d%%";
    private Button button;
    CircleProgressBar circleProgressBar;

    private int total_setnum = 0;
    private int complete_setnum = 0;
    private int prograss_num = 0;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        circleProgressBar = rootView.findViewById(R.id.cpb_circlebar);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Config.selected_weekday = Config.today_hangle();
        Config.setToday();
        Config.setWeek();
        recyclerView = (RecyclerView) getView().findViewById(R.id.home_recycler); // 정의 후에 사용해야함!.
        if(userConfig.getInstance().getWeekData() == null) {
            RetrofitObject.getInstance().GetInfo(this);
        }
        else{
            onInfoGetSuccesse();
        }
        button = getView().findViewById(R.id.exercise_start_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Days_routineList.size() == 0) {
                    Toast.makeText(getActivity(), "오늘의 운동 루틴을 만들어 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    //Intent intent  = new Intent(view.getContext(), CameraXLivePreviewActivity.class );
                    Intent intent = new Intent(view.getContext(), RoutineCameraXLivePreviewActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    public void set_circle() {
        Days_routineList = userConfig.getInstance().getWeekData().getDateInfoList(Config.today_string());
        total_setnum = 0;
        complete_setnum = 0;
        prograss_num = 0;
        for (int i = 0; i < Days_routineList.size(); ++i) {
            complete_setnum = complete_setnum + Days_routineList.get(i).getSetComplete();
            total_setnum = total_setnum + Days_routineList.get(i).getSetNum();
        }
        if (total_setnum != 0) {
            prograss_num = (int) (100 * complete_setnum / total_setnum);
        } else {
            prograss_num = 0;
        }
        Log.e("routinelistsize", Integer.toString(Days_routineList.size()));
        Log.e("total_setup", Integer.toString(total_setnum));
        Log.e("complete_setup", Integer.toString(complete_setnum));
        circleProgressBar.setProgress(prograss_num);
    }
    public void setRecyclerView(){
        routineListRecyclerViewAdapter = new HomeViewAdapter(getActivity(), userConfig.getInstance().getWeekData().getDateInfoList(Config.today_string()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(routineListRecyclerViewAdapter);
    }

    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }

    @Override
    public void onInfoGetSuccesse() {
        setRecyclerView();
        set_circle();
    }

    @Override
    public void onInfoCreated(int position) {

    }

    @Override
    public void onInfoChanged(int position) {

    }

    @Override
    public void onInfoDeleted(int position) {

    }
}