package com.fooddepot.activity.cook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fooddepot.R;
import com.fooddepot.activity.CookActivity;
import com.fooddepot.activity.order.CalculateDistance;
import com.fooddepot.service.api.CookService;
import com.fooddepot.service.api.ItemService;

import com.fooddepot.service.impl.CookServiceImpl;
import com.fooddepot.service.impl.ItemServiceImpl;
import com.fooddepot.storage.api.ProfilePicService;
import com.fooddepot.storage.impl.ProfilePicServiceImpl;
import com.fooddepot.ui.api.UICookService;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.util.DAOUtil;
import com.fooddepot.vo.Cook;
import com.fooddepot.vo.Item;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddMenuActivity extends AppCompatActivity implements UIItemService,UICookService, View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE=1;

    static Button datebtn, timebtn, datetill,timetill,quantitybtn,photo_btn,cnclbtn,save_btn;
    ImageView imageView;
    int year_s, month_s, day_s,hour_s,min_s;
    static final int CALENDAR_ID=0;
    EditText name,price,description;
    Spinner category, classification;
    ItemService itemService = null;
    CookService cookService;
    String itemID;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    Cook cook;
    ProfilePicService profilePicService ;
    Uri selectedImage;
    CalculateDistance cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        cookService=new CookServiceImpl();
        cookService.read(currentUser.getUid(),AddMenuActivity.this);
        Intent intent = getIntent();

        name=(EditText)findViewById(R.id.itemName_edtxt);
        category=(Spinner)findViewById(R.id.cuisine);
        price=(EditText)findViewById(R.id.price_edtxt);
        classification=(Spinner)findViewById(R.id.category_spn);
        description=(EditText)findViewById(R.id.itmDsc_edtxt);
        datebtn = (Button)findViewById(R.id.date_btn);
        timebtn = (Button)findViewById(R.id.time_btn);
        datetill = (Button)findViewById(R.id.datetil_btn);
        timetill = (Button)findViewById(R.id.timetil_btn);
        quantitybtn = (Button)findViewById(R.id.qnty_btn);
        photo_btn = (Button)findViewById(R.id.photo_btn);
        cnclbtn = (Button)findViewById(R.id.cancel_btn);
        cnclbtn.setOnClickListener(this);
        save_btn = (Button)findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        uploadPhoto();

        if(intent.hasExtra("ITEM_ID")){
            itemService = new ItemServiceImpl();
            itemID = intent.getStringExtra("ITEM_ID");
//            Log.d("Id received",itemID);
            itemService.read(itemID, this);
            save_btn.setText("Update");
        }
    }

    public void uploadPhoto(){
        photo_btn =(Button)findViewById(R.id.photo_btn);
        photo_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent galeryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(i, RESULT_LOAD_IMAGE);

                //Intent galeryIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeryIntent,RESULT_LOAD_IMAGE);
                imageView = new ImageView(getApplicationContext());
                RelativeLayout rl = (RelativeLayout)findViewById(R.id.menuRL);
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
            selectedImage=data.getData();
            imageView.setImageURI(selectedImage);
            //ProfilePicServiceImpl profilePicService = new ProfilePicServiceImpl();

            //profilePicService.uploadFromUri(selectedImage,"menu");

        }
    }

    public void showCalendar(View view){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("the selected " + mDay);
        DatePickerDialog dialog = new DatePickerDialog(this,
                new mDateSetListener(), mYear, mMonth, mDay);
        dialog.show();
    }

    public void showClock(View view){
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(this,
                new mTimeSetListener(), mHour, mMinute,true);
        dialog.show();
    }

    public void showCalendarTil(View view){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("the selected " + mDay);
        DatePickerDialog dialog = new DatePickerDialog(this,
                new mDateTilSetListener(), mYear, mMonth, mDay);
        dialog.show();
    }

    public void showClockTil(View view){
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(this,
                new mTimeTilSetListener(), mHour, mMinute,true);
        dialog.show();
    }

    public void selectqnty (View view){
        mQuantitySetListener newFragment = new mQuantitySetListener();
        newFragment.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.save_btn://getText(R.id.save_btn):
                itemService = new ItemServiceImpl();
                String message="";
                String address=cook.getAddressLine1()+" "+cook.getAddressLine2()+" "+cook.getState()+" "+cook.getZipcode();
                Item item = new Item(name.getText().toString(), category.getSelectedItem().toString(), description.getText().toString(),
                        Float.parseFloat(price.getText().toString()), Integer.parseInt(quantitybtn.getText().toString()),
                        "phototpath",classification.getSelectedItem().toString(),
                        datebtn.getText().toString(),datetill.getText().toString(), timebtn.getText().toString(), timetill.getText().toString(),0,
                        cook.getUid(),0,address);
                cal = new CalculateDistance();
                Barcode.GeoPoint geoPoint= cal.getLocationFromAddress(this,address);
                item.setLatitude(geoPoint.lat);
                item.setLongitude(geoPoint.lng);
                item.setNickName(cook.getNickName());
                if(itemID !=null){
                    if(selectedImage==null)
                        item.setPhotoPath("menu/"+"food_default.png");

                    else
                        item.setPhotoPath("menu/"+itemID+".jpg");
                    item.setItemId(itemID);
                    itemService.update(itemID,item);
                    message="Item updated successfully";
                }
                else {
                    String itemId = DAOUtil.getDatabaseReference().push().getKey();
                    item.setItemId(itemId);
                    if(selectedImage==null)
                        item.setPhotoPath("menu/"+"food_default.png");

                    else
                        item.setPhotoPath("menu/"+itemId+".jpg");

                    itemService.add(item);
                    profilePicService = new ProfilePicServiceImpl();
                    profilePicService.uploadFromUri(selectedImage,"menu",itemId+".jpg");
                    message = "Item added successfully";
                }
                Toast.makeText(AddMenuActivity.this, message, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,CookActivity.class);
                startActivity(i);
                break;
        }

    }

    @Override
    public void displayAllItems(List<Item> items) {

    }

    @Override
    public void displayItemsList(Map<String,Item> items){

    }

    @Override
    public void displayItem(Item item) {
        String categories[]=getResources().getStringArray(R.array.cuisine);
        int catPos=0;
        for(int i=0; i < categories.length; i++)
            if(categories[i].contains(item.getCategory()))
                catPos = i;

        String classifications[]=getResources().getStringArray(R.array.category);
        int clasPos=0;
        for(int i=0; i < classifications.length; i++)
            if(classifications[i].equals(item.getClassification()))
                clasPos = i;

        name.setText(item.getName());
        category.setSelection(catPos);
        description.setText(item.getDescription());
        price.setText(item.getPrice()+"");
        quantitybtn.setText(item.getQuantity()+"");
        //set image path
        classification.setSelection(clasPos);
        datebtn.setText(item.getDateStart());
        datetill.setText(item.getDateEnd());
        timebtn.setText(item.getTimeStart());
        timetill.setText(item.getTimeEnd());


        Log.d("name",item.getName());
        Log.d("description",item.getDescription());

    }

    @Override
    public void displayAllCooks(List<Cook> cook) {

    }

    @Override
    public void displayCook(Cook cook) {
        this.cook=cook;

    }


    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            datebtn.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));
            System.out.println(datebtn.getText().toString());


        }
    }

    class mDateTilSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            datetill.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));
            System.out.println(datebtn.getText().toString());


        }
    }

    class mTimeSetListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // TODO Auto-generated method stub
            // getCalender();
            int mHour = hour;
            int mMinute = minute;
            timebtn.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mHour).append(":").append(mMinute).append(" "));
            System.out.println(datebtn.getText().toString());

        }
    }

    class mTimeTilSetListener implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // TODO Auto-generated method stub
            // getCalender();
            int mHour = hour;
            int mMinute = minute;
            timetill.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mHour).append(":").append(mMinute).append(" "));
            System.out.println(datebtn.getText().toString());

        }
    }

    public static class mQuantitySetListener extends DialogFragment implements NumberPicker.OnValueChangeListener {

        private NumberPicker.OnValueChangeListener valueChangeListener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final NumberPicker numberPicker = new NumberPicker(getActivity());
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(20);

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
