package com.gauravk.bubblebarsample.DB.ShowRoutine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.gauravk.bubblebarsample.BottomBarActivity;
import com.gauravk.bubblebarsample.fragment.RoutineUpdateDialogF;
import com.gauravk.bubblebarsample.Dto.info;
import com.gauravk.bubblebarsample.R;
import com.gauravk.bubblebarsample.cfg.Config;
import com.gauravk.bubblebarsample.cfg.RetrofitObject;
import com.gauravk.bubblebarsample.cfg.userConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;


public class RoutineViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<info> infoList;

    public RoutineViewAdapter(Context context, List<info> infoList) {
        this.context = context;
        this.infoList = infoList;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.routine_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final int itemPosition = position;
        info routine = infoList.get(position);
        Log.i("Holder_routine",Integer.toString(position));
        holder.Exercise_nameTextView.setText(String.format("%d. %s",routine.getSequence(),routine.getExername()));
        holder.Set_numTextView.setText(String.valueOf(routine.getSetNum()));
        holder.Repeat_numTextView.setText(String.valueOf(routine.getRepeatNum()));
        holder.Rest_timeTextView.setText(String.valueOf(routine.getRestTime()));
        Log.i("Holder_routine", String.format("ID = %d, Reg_no = %d, name = %s, Set_num = %d, Repeat_num = %d, Rest_time = %d", routine.getSequence() , routine.getSequence(), routine.getExername() , routine.getSetNum(), routine.getRepeatNum(), routine.getRestTime()));


        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("?????? ????????? ??????????????????????");
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
                                RetrofitObject.getInstance().DeleteInfo(routine,userConfig.getInstance().getWeekData().getPosition(routine.getDate(),routine.getSequence()));
                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoutineUpdateDialogF routineUpdateDialogFragment = RoutineUpdateDialogF.newInstance(userConfig.getInstance().getWeekData().getPosition(routine.getDate(),routine.getSequence()));
                routineUpdateDialogFragment.show(((BottomBarActivity) context).getSupportFragmentManager(), Config.UPDATE_Routine);
            }
        });
    }
    public void resetRoutineList(){
        infoList.clear();
    }
    private void deleteRoutine(int position) {

    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

}
