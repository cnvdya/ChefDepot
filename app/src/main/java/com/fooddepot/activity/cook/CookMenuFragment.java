package com.fooddepot.activity.cook;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.fooddepot.R;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CookMenuFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGE=1;

    static Button datebtn, timebtn, datetill,timetill,quantitybtn,photo_btn;
    ImageView imageView;
    int year_s, month_s, day_s,hour_s,min_s;
    static final int CALENDAR_ID=0;
    EditText date;
    DatePickerDialog datePickerDialog;

    public CookMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cook_menu, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showCalendar();
        showClock();
        showQuantity();
        uploadPhoto();
        showCalendarTill();
        showClockTill();

    }

    public void uploadPhoto(){
        photo_btn =(Button)getView().findViewById(R.id.photo_btn);
        photo_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent galeryIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeryIntent,RESULT_LOAD_IMAGE);
                imageView = new ImageView(getView().getContext());
                RelativeLayout rl = (RelativeLayout)getView().findViewById(R.id.menuRL);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(200, 200);
                lp.topMargin=20;
                lp.leftMargin=20;
                lp.addRule(RelativeLayout.END_OF,photo_btn.getId());
                lp.addRule(RelativeLayout.ALIGN_BASELINE,quantitybtn.getId());
                rl.addView(imageView, lp);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data !=null){
            Uri selectedImage=data.getData();
            imageView.setImageURI(selectedImage);
        }
    }

    public void showCalendar(){
        datebtn = (Button)getView().findViewById(R.id.date_btn);
        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
    }

    public void showClock(){
        timebtn = (Button)getView().findViewById(R.id.time_btn);
        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectTimeFragment();
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        });
    }

    public void showCalendarTill(){
        datetill = (Button)getView().findViewById(R.id.dateFrom_btn);
        datetill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragmentTill();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
    }

    public void showClockTill(){
        timetill = (Button)getView().findViewById(R.id.timeFrom_btn);
        timetill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectTimeFragmentTill();
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        });
    }

    public void showQuantity(){
        quantitybtn = (Button)getView().findViewById(R.id.qnty_btn);
        quantitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectQuantityFragment();
                newFragment.show(getFragmentManager(), "QuantityPicker");
            }
        });
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = calendar.get(Calendar.MINUTE);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {
            datebtn.setText(month+"/"+day+"/"+year);
        }

    }

    public static class SelectDateFragmentTill extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = calendar.get(Calendar.MINUTE);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {
            datetill.setText(month+"/"+day+"/"+year);
        }

    }

    public static class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = calendar.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, mHour, mMinute,true);
        }

        public void onTimeSet(TimePicker view, int hour, int minute) {
            populateSetTime(hour, minute);
        }
        public void populateSetTime(int hour, int minute) {
            timebtn.setText(hour+":"+minute);

        }

    }

    public static class SelectTimeFragmentTill extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = calendar.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, mHour, mMinute,true);
        }

        public void onTimeSet(TimePicker view, int hour, int minute) {
            populateSetTime(hour, minute);
        }
        public void populateSetTime(int hour, int minute) {
            timetill.setText( hour+":"+minute);

        }

    }

    public static class SelectQuantityFragment extends DialogFragment implements NumberPicker.OnValueChangeListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final NumberPicker numberPicker = new NumberPicker(getActivity());
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(100);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select quantity");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onValueChange(numberPicker,
                            numberPicker.getValue(), numberPicker.getValue());
                }
            });

            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onValueChange(numberPicker,
                            numberPicker.getValue(), numberPicker.getValue());
                }
            });

            builder.setView(numberPicker);
            return builder.create();
        }


        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            quantitybtn.setText(i+"");
        }

    }





}
