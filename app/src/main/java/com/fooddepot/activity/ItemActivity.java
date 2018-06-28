package com.fooddepot.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fooddepot.R;
import com.fooddepot.service.api.ItemService;
import com.fooddepot.service.impl.ItemServiceImpl;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.vo.Cook;
import com.fooddepot.vo.Item;

import java.util.List;
import java.util.Map;

public class ItemActivity extends Activity implements UIItemService, View.OnClickListener {

    private Button addItem;
    private Button readItem;
    private Button deleteItem;
    private ItemService itemService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        addItem = (Button) findViewById(R.id.add_item);
        addItem.setOnClickListener(this);
        readItem = (Button) findViewById(R.id.read_item);
        readItem.setOnClickListener(this);

        deleteItem = (Button) findViewById(R.id.delete_item);
        deleteItem.setOnClickListener(this);

        itemService = new ItemServiceImpl();


    }


    @Override
    public void displayAllItems(List<Item> items) {

    }

    @Override
    public void displayItem(Item item) {

    }

    @Override
    public void displayItemsList(Map<String,Item> items){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_item:
//                Cook cook = new Cook("uid1", "name1", "address1", "email1", "phoneNumber1", "profilePicPath1");
//                Item item = new Item("name1", "cat1", "desc1", 2, 1, "path1", "class1", "12345", "sched1", cook);
//                itemService.add(item);
                break;
            case R.id.read_item:
                itemService.read("-LBEghqNuJVUcauzr87", this);
                break;

            case R.id.delete_item:
                itemService.delete("-LBEghqNuJVUcauzr87");
                break;
            case R.id.update_item:
//                Cook cook1 = new Cook("uid1", "name1", "address1", "email1", "phoneNumber1", "profilePicPath1");
//                Item item1 = new Item("name1updated", "cat1", "desc1", 2, 1, "path1", "class1", "12345", "sched1", cook1);
//                itemService.update("-LBEghqNuJVUcauzr87", item1);
                break;


        }
    }
}