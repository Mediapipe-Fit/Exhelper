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
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Locale;


public class RoutineCreateDialogF extends DialogFragment {


    //private EditText Exercise_nameEditText;
    private EditText Set_numEditText;
    private EditText Repeat_numEditText;
    private EditText Rest_timeEditText;
    private Button createButton;
    private Button cancelButton;

    private Spinner Excercise_name;
    private Spinner RegNo;
    private int Set_num = -1;
    private int Repeat_num = -1;
    private int Rest_time = -1;
    private int Regno = -1;
    private String temp = "";
    private String exername;
    private String email;
    private String date;

    private QueryClass DBQueryClass; //DB 소환

    private NumberPicker Set,Repeat,Rest;
    public RoutineCreateDialogF() {
        // Required empty public constructor
    }

    public static RoutineCreateDialogF newInstance(String title){
        RoutineCreateDialogF RoutineCreateDialogF = new RoutineCreateDialogF();
        Bundle args = new Bundle();
        args.putString("title", title);
        RoutineCreateDialogF.setArguments(args);

        RoutineCreateDialogF.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return RoutineCreateDialogF;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.routine_create_dialog_f, container, false);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        Excercise_name = view.findViewById(R.id.Exercise);
        ArrayAdapter Exercise_Adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Exercises, android.R.layout.simple_spinner_item);
        Exercise_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Excercise_name.setAdapter(Exercise_Adapter);

        RegNo = view.findViewById(R.id.Spinner_RegNO);
        setSequenceAdapter();


        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);
        /*
        createButton.setOnClickListener(new View.OnClickListener() { // 내부 DB
            @Override
            public void onClick(View view) {
                temp = Excercise_name.getSelectedItem().toString();
                Regno = Integer.parseInt(RegNo.getSelectedItem().toString());
                Set_num = Set.getValue();
                Repeat_num = Repeat.getValue();
                Rest_time = Rest.getValue();

                //만들때는 0으로
                Routine routine = new Routine(-1, temp,Regno, Set_num, Repeat_num, Rest_time,0,0);
                Log.i("Holder_routine_Make", String.format("ID = %d, Reg_no = %d, name = %s, Set_num = %d, Repeat_num = %d, Rest_time = %d", routine.getId() , routine.getRegNO(), routine.getName() , routine.getSet_num(), routine.getRepeat_num(), routine.getRest_time()));

                QueryClass databaseQueryClass = new QueryClass(getContext());

                long id = databaseQueryClass.insertRoutine(routine);

                if(id>0){
                    routine.setId(id);
                    RoutineCreateListener.onRoutineCreated(routine);
                    getDialog().dismiss();
                }
                //Log.i("DB_Insert_Routine_in_D", String.format("ID = %d, name = %s, Set_num = %d, Repeat_num = %d, Rest_time = %d", id , Routine.getName() , Routine.getSet_num(), Routine.getRepeat_num(), Routine.getRepeat_num()));

            }
        });*/
        createButton.setOnClickListener(new View.OnClickListener() { // 서버 DB
            @Override
            public void onClick(View view) {
                date = Config.weekDate.get(Config.selected_weekday);
                email = userConfig.getInstance().getEmail();
                exername = Excercise_name.getSelectedItem().toString();
                Regno = Integer.parseInt(RegNo.getSelectedItem().toString());
                Set_num = Set.getValue();
                Repeat_num = Repeat.getValue();
                Rest_time = Rest.getValue();
                info tempInfo = new info(date,email,exername,Regno,Set_num,Repeat_num,Rest_time,0,0);
                //만들때는 0으로
                RetrofitObject.getInstance().CreateInfo(tempInfo);
                ///RoutineCreateListener.onRoutineCreated(tempInfo);

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
        ArrayAdapter RegNo_Adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,userConfig.getInstance().getWeekData().getSequence(Config.selectedString()));
        RegNo.setAdapter(RegNo_Adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
        Set = set_nummber_picker(R.id.number_picker_Set_num,3, 1, 20);
        Repeat = set_nummber_picker(R.id.number_picker_Repeat_num,10, 1, 50);
        Rest = set_nummber_picker(R.id.number_picker_Rest_time,30, 10, 60);
    }

    public NumberPicker set_nummber_picker(int id, int num, int min_num, int max_num){
        final NumberPicker numberPicker = getView().findViewById(id);

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