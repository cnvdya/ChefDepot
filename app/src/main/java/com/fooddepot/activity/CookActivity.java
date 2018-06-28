package com.fooddepot.activity;

import com.fooddepot.R;
import com.fooddepot.service.api.CookService;
import com.fooddepot.service.api.ItemService;
import com.fooddepot.service.impl.CookServiceImpl;
import com.fooddepot.ui.api.UICookService;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.vo.Cook;
import com.fooddepot.vo.Item;

import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

public class CookActivity extends AppCompatActivity {

    TabLayout tabLayout;
    Toolbar toolbar;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem profile,menu,orders;


//    ItemService itemService = null;
//    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);


        toolbar= (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        tabLayout =(TabLayout) findViewById(R.id.tablayout);

        profile = (TabItem)findViewById(R.id.profile);
        menu = (TabItem)findViewById(R.id.menu);
        orders = (TabItem)findViewById(R.id.orders);

        viewPager =(ViewPager) findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {

                } else if (tab.getPosition() == 2) {
                    new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
                }
//                else if (tab.getPosition() == 2) {
//                    new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
//                }
                else {
                    tab.select();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        tabLayout.setupWithViewPager(viewPager);

    }

}


