package com.gauravk.bubblebarsample.DB.ShowRoutine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gauravk.bubblebarsample.DB.CreateRoutine.Routine;
import com.gauravk.bubblebarsample.DB.QueryClass;
import com.gauravk.bubblebarsample.Dto.info;
import com.gauravk.bubblebarsample.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;


public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewHolder> {

    private Context context;
    private List<info> infoList;

    public HomeViewAdapter(Context context, List<info> infoList) {
        this.context = context;
        this.infoList = infoList;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.routine_item_main, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        final int itemPosition = position;
        final info routine = infoList.get(position);

        holder.Exercise_nameTextView.setText(String.format("%d. %s",routine.getSequence(),routine.getExername()));
        holder.Set_numTextView.setText(String.valueOf(routine.getSetNum()));
        holder.Repeat_numTextView.setText(String.valueOf(routine.getRepeatNum()));
        holder.Rest_timeTextView.setText(String.valueOf(routine.getRestTime()));
    }



    @Override
    public int getItemCount() {
        return infoList.size();
    }

}
