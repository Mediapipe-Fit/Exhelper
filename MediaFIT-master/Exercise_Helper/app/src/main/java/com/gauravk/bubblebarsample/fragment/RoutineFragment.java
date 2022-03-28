package com.gauravk.bubblebarsample.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gauravk.bubblebarsample.DB.CreateRoutine.Routine;
import com.gauravk.bubblebarsample.DB.QueryClass;
import com.gauravk.bubblebarsample.DB.ShowRoutine.RoutineViewAdapter;
import com.gauravk.bubblebarsample.Dto.InfoChangeListener;
import com.gauravk.bubblebarsample.R;
import com.gauravk.bubblebarsample.cfg.Config;
import com.gauravk.bubblebarsample.cfg.userConfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutineFragment extends Fragment implements InfoChangeListener {

    private QueryClass databaseQueryClass;
    private TextView Title;
    private List<Routine> Days_routineList;

    private TextView routineListEmptyTextView;
    private RecyclerView recyclerView;
    private RoutineViewAdapter routineListRecyclerViewAdapter;
    private TextView dateTextView;
    Display display;

    private ArrayList<Button> Btns;


    public static RoutineFragment newInstance() {
        RoutineFragment fragment = new RoutineFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseQueryClass = new QueryClass(getActivity());
        display = getActivity().getWindowManager().getDefaultDisplay();
        return inflater.inflate(R.layout.fragment_routine_list, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        userConfig.getInstance().setRoutineInfoListener(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        setBtns();
        recyclerView = (RecyclerView) getView().findViewById(R.id.RoutineRecyclerView);
        routineListEmptyTextView = (TextView) getView().findViewById(R.id.emptyRoutineListTextView);
        dateTextView = getView().findViewById(R.id.date);
        Btns.get(Config.index-1).callOnClick();
        viewVisibility();
        change_View();
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRoutineCreateDialog();
            }
        });

        FloatingActionButton del = (FloatingActionButton) getView().findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Days_routine_delete();
            }
        });
    }
    public void setBtns(){
        Btns = new ArrayList<>();
        ArrayList<Integer> Weekdays = new ArrayList<>(Arrays.asList(R.id.sunday,R.id.monday,R.id.tuesday,R.id.wednesday,R.id.thursday,R.id.friday,R.id.saturday));
        for(int i=0;i<7;++i){
            Button cur = (Button) getView().findViewById(Weekdays.get(i));
            Btns.add(cur);
        }
        Set_onButtonClick(Btns);
    }
    public void Set_onButtonClick(ArrayList<Button> btns) {
        for(int i=0;i<btns.size();++i){
            int finalI = i;
            btns.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reset_button(btns);
                    btns.get(finalI).setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.weekday_active));
                    Log.i("Touched", String.valueOf(btns.get(finalI).getText()));
                    Config.selected_weekday = String.valueOf(btns.get(finalI).getText());
                    dateTextView.setText(Config.weekDate.get(Config.selected_weekday));
                    change_View();
                }
            });
        }
    }
    public void reset_button(ArrayList<Button> btns){
        for(int i=0;i<btns.size();++i){
            int finalI = i;
            btns.get(finalI).setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.weekday_inactive));
        }
    }
    public void change_View() {
        routineListRecyclerViewAdapter = new RoutineViewAdapter(getActivity(), userConfig.getInstance().getWeekData().getDateInfoList(Config.selectedString()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(routineListRecyclerViewAdapter);
        viewVisibility();
    }


    public void Days_routine_delete(){
        if(Days_routineList.size() == 0){
            Toast.makeText(getActivity(),"삭제할 루틴이 없습니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(Config.selected_weekday+"요일 루틴을 모두 삭제 하시겠습니까?");
        alertDialogBuilder.setPositiveButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        boolean isAllDeleted = databaseQueryClass.deleteDayRoutines(Config.selected_weekday);

                        if(isAllDeleted){
                            Days_routineList.clear();
                            routineListRecyclerViewAdapter.notifyDataSetChanged();
                            viewVisibility();
                        }
                    }
                });
        alertDialogBuilder.create(); // 만들고
        alertDialogBuilder.show(); // 보여준다.
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // 메뉴바 사용할때.


        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        Log.d("test",Config.weekDate.get(Config.selected_weekday));
        if(userConfig.getInstance().getWeekData().getDateInfoList(Config.selectedString()).size() == 0)
            routineListEmptyTextView.setVisibility(View.VISIBLE);
        else
            routineListEmptyTextView.setVisibility(View.GONE);
    }

    private void openRoutineCreateDialog() {
        RoutineCreateDialogF routineCreateDialogFragment = RoutineCreateDialogF.newInstance(Config.selected_weekday+"요일 루틴");
        routineCreateDialogFragment.show(getActivity().getSupportFragmentManager(), Config.CREATE_Routine);
    }


    @Override
    public void onInfoGetSuccesse() {
        routineListRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onInfoCreated(int position) {
        routineListRecyclerViewAdapter.notifyItemInserted(position);

    }

    @Override
    public void onInfoChanged(int position) {
        routineListRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onInfoDeleted(int position) {
        routineListRecyclerViewAdapter.notifyItemRemoved(position);

    }
}
