package com.gauravk.bubblebarsample.fragment;


import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.gauravk.bubblebarsample.DB.QueryClass;
import com.gauravk.bubblebarsample.Dto.info;
import com.gauravk.bubblebarsample.R;
import com.gauravk.bubblebarsample.cfg.Config;
import com.gauravk.bubblebarsample.cfg.RetrofitObject;
import com.gauravk.bubblebarsample.cfg.userConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.List;
import java.util.Locale;


public class RoutineUpdateDialogF extends DialogFragment {

    private static int routineItemID;

    private info selectedInfo;

    private EditText Set_numEditText;
    private EditText Repeat_numEditText;
    private EditText Rest_timeEditText;
    private Button updateButton;
    private Button cancelButton;

    private Spinner Excercise_name;
    private Spinner RegNo;


    private String Exername = "";
    private int Regno = -1;
    private int Set_num = -1;
    private int Repeat_num = -1;
    private int Rest_time = -1;
    private int SequencePosition = -1;

    private QueryClass DBQueryClass;
    private NumberPicker Set,Repeat,Rest;
    public RoutineUpdateDialogF() {
        // Required empty public constructor
    }

    public static RoutineUpdateDialogF newInstance(int position){
        routineItemID = position;
        RoutineUpdateDialogF routineUpdateDialogFragment = new RoutineUpdateDialogF();
        Bundle args = new Bundle();
        args.putString("title", Config.selected_weekday+"요일 루틴 업데이트");
        routineUpdateDialogFragment.setArguments(args);

        routineUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return routineUpdateDialogFragment;
    }

    public void setSelectedInfo(info Info){
        selectedInfo = Info;
    }

    public int getIndex(String s){
        String[] Excercise_names = getResources().getStringArray(R.array.Exercises);
        for(int i=0;i<Excercise_names.length ;++i){
            if(s.contains(Excercise_names[i])){
                return i;
            }
        }
        return 0;
    }
    public void setSequencePosition(List<String> Temp){

        for(int i = 0; i < Temp.size();++i ){
            if(Temp.get(i) == String.valueOf(selectedInfo.getSequence())){
                SequencePosition = i;
                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.routine_update_dialog_f, container, false);

        updateButton = view.findViewById(R.id.updateRoutineInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        Excercise_name = view.findViewById(R.id.Exercise);
        ArrayAdapter Exercise_Adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Exercises, android.R.layout.simple_spinner_item);
        Exercise_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Excercise_name.setAdapter(Exercise_Adapter);

        RegNo = view.findViewById(R.id.Spinner_RegNO);
        setSelectedInfo(userConfig.getInstance().getWeekData().getDateInfo(Config.selectedString(),routineItemID));


        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        setSequenceAdapter();
        Set = view.findViewById(R.id.number_picker_Set_num);
        Repeat = view.findViewById(R.id.number_picker_Repeat_num);
        Rest = view.findViewById(R.id.number_picker_Rest_time);
        //Logger.d(selectedInfo);
        //Logger.d(routineItemID);

        Excercise_name.setSelection(getIndex(selectedInfo.getExername()));
        RegNo.setSelection(SequencePosition);
        Set = set_nummber_picker(Set, (int) selectedInfo.getSetNum(),1, 20);
        Repeat = set_nummber_picker(Repeat, (int) selectedInfo.getRepeatNum(),1, 50);
        Rest = set_nummber_picker(Rest,(int) selectedInfo.getRestTime(),10, 60);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exername = Excercise_name.getSelectedItem().toString();
                Regno = Integer.parseInt(RegNo.getSelectedItem().toString());
                Set_num = Set.getValue();
                Repeat_num = Repeat.getValue();
                Rest_time = Rest.getValue();
                Logger.d(selectedInfo);
                info temp = new info(selectedInfo.getDate(), selectedInfo.getEmail(), Exername, Regno,Set_num,Repeat_num,Rest_time, selectedInfo.getSetComplete(), selectedInfo.getCurrent());
                RetrofitObject.getInstance().UpdateInfo(selectedInfo.getExername(), selectedInfo.getSequence(),temp,routineItemID);
                userConfig.getInstance().getWeekData().setDateInfo(selectedInfo.getDate(),routineItemID,temp);
                //selectedInfo = temp;
                getDialog().dismiss();
                //Log.i("DB_Update_Routine_in_D", String.format("ID = %d, RegNO = %d, name = %s, Set_num = %d, Repeat_num = %d, Rest_time = %d", -1 , Regno, temp , Set_num, Repeat_num, Repeat_num));
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }
    public void setSequenceAdapter(){
        List<String> Temp = userConfig.getInstance().getWeekData().getMySequence(Config.selectedString(), selectedInfo.getSequence());
        setSequencePosition(Temp);
        ArrayAdapter RegNo_Adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item, Temp);
        RegNo.setAdapter(RegNo_Adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        Logger.addLogAdapter(new AndroidLogAdapter());
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }


    public NumberPicker set_nummber_picker(NumberPicker numberPicker, int num, int min_num, int max_num){
        // Set divider color
        numberPicker.setDividerColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        numberPicker.setDividerColorResource(R.color.colorPrimary);

        // Set formatter
        numberPicker.setFormatter(getString(R.string.number_picker_formatter));
        numberPicker.setFormatter(R.string.number_picker_formatter);

        // Set selected text color
        numberPicker.setSelectedTextColor(ContextCompat.getColor(getContext(), R.color.purple_inactive));
        numberPicker.setSelectedTextColorResource(R.color.red_active);

        // Set selected text size
        numberPicker.setSelectedTextSize(getResources().getDimension(R.dimen.selected_text_size));
        numberPicker.setSelectedTextSize(R.dimen.selected_text_size);

        // Set selected typeface
        numberPicker.setSelectedTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setSelectedTypeface(getString(R.string.roboto_light), Typeface.NORMAL);
        numberPicker.setSelectedTypeface(getString(R.string.roboto_light));
        numberPicker.setSelectedTypeface(R.string.roboto_light, Typeface.NORMAL);
        numberPicker.setSelectedTypeface(R.string.roboto_light);

        // Set text color
        numberPicker.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_grey));
        numberPicker.setTextColorResource(R.color.dark_grey);

        // Set text size
        numberPicker.setTextSize(getResources().getDimension(R.dimen.text_size));
        numberPicker.setTextSize(R.dimen.text_size);

        // Set typeface
        numberPicker.setTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setTypeface(getString(R.string.roboto_light), Typeface.NORMAL);
        numberPicker.setTypeface(getString(R.string.roboto_light));
        numberPicker.setTypeface(R.string.roboto_light, Typeface.NORMAL);
        numberPicker.setTypeface(R.string.roboto_light);


        // Set value
        numberPicker.setMaxValue(max_num);
        numberPicker.setMinValue(min_num);
        numberPicker.setValue(num);

        // Set string values
//        String[] data = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
//        numberPicker.setMinValue(1);
//        numberPicker.setMaxValue(data.length);
//        numberPicker.setDisplayedValues(data);

        // Set fading edge enabled
        numberPicker.setFadingEdgeEnabled(true);

        // Set scroller enabled
        numberPicker.setScrollerEnabled(true);

        // Set wrap selector wheel
        numberPicker.setWrapSelectorWheel(true);

        // Set accessibility description enabled
        numberPicker.setAccessibilityDescriptionEnabled(true);

        // OnClickListener
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Click on current value");
            }
        });

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("TAG", String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
            }
        });

        // OnScrollListener
        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker picker, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    Log.d("TAG", String.format(Locale.US, "newVal: %d", picker.getValue()));
                }
            }
        });
        return numberPicker;
    }

}
