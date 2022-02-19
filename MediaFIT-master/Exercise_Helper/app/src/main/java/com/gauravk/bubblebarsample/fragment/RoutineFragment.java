package com.gauravk.bubblebarsample.fragment;


import static com.gauravk.bubblebarsample.cfg.MyGlobal.today_hangle;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gauravk.bubblebarsample.BottomBarActivity;
import com.gauravk.bubblebarsample.DB.CreateRoutine.Routine;
import com.gauravk.bubblebarsample.DB.CreateRoutine.RoutineCreateDialogF;
import com.gauravk.bubblebarsample.DB.CreateRoutine.RoutineCreateListener;
import com.gauravk.bubblebarsample.DB.QueryClass;
import com.gauravk.bubblebarsample.DB.ShowRoutine.RoutineViewAdapter;
import com.gauravk.bubblebarsample.R;
import com.gauravk.bubblebarsample.cfg.Config;
import com.gauravk.bubblebarsample.cfg.MyGlobal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutineFragment extends Fragment implements RoutineCreateListener{

    private QueryClass databaseQueryClass;
    private TextView Title;
    private List<Routine> Days_routineList;

    private TextView routineListEmptyTextView;
    private RecyclerView recyclerView;
    private RoutineViewAdapter routineListRecyclerViewAdapter;
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
        Logger.addLogAdapter(new AndroidLogAdapter());
        setBtns();
        Config.selected_weekday = MyGlobal.today_hangle();
        recyclerView = (RecyclerView) getView().findViewById(R.id.RoutineRecyclerView);
        routineListEmptyTextView = (TextView) getView().findViewById(R.id.emptyRoutineListTextView);
        Btns.get(MyGlobal.index-1).callOnClick();
        viewVisibility();

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
        ArrayList<Integer> Weekdays = new ArrayList<>(Arrays.asList(R.id.Sun,R.id.Mon,R.id.Tue,R.id.Wed,R.id.Thu,R.id.Fri,R.id.Sat));
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
    public void change_View(){
        Days_routineList = new ArrayList<Routine>();
        Days_routineList = databaseQueryClass.getDaysRoutine(Config.selected_weekday);
        for(int i=0;i<Days_routineList.size();++i){
            Routine routine = Days_routineList.get(i);
            Log.i("Holder_routine_Fragment", String.format("ID = %d, Reg_no = %d, name = %s, Set_num = %d, Repeat_num = %d, Rest_time = %d", routine.getId() , routine.getRegNO(), routine.getName() , routine.getSet_num(), routine.getRepeat_num(), routine.getRest_time()));

        }
        routineListRecyclerViewAdapter = new RoutineViewAdapter(getActivity(), Days_routineList);
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
        if(Days_routineList.isEmpty())
            routineListEmptyTextView.setVisibility(View.VISIBLE);
        else
            routineListEmptyTextView.setVisibility(View.GONE);
    }

    private void openRoutineCreateDialog() {
        RoutineCreateDialogF routineCreateDialogFragment = RoutineCreateDialogF.newInstance(Config.selected_weekday+"요일 루틴", this::onRoutineCreated);
        routineCreateDialogFragment.show(getActivity().getSupportFragmentManager(), Config.CREATE_Routine);
    }

    @Override
    public void onRoutineCreated(Routine routine) {
        routineListRecyclerViewAdapter.notifyDataSetChanged();
        change_View();
        Logger.d(routine.getName());
    }

}
