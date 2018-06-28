package com.fooddepot.activity.cook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.fooddepot.R;
import com.fooddepot.service.api.ItemService;
import com.fooddepot.service.impl.ItemServiceImpl;
import com.fooddepot.storage.api.ProfilePicService;
import com.fooddepot.storage.impl.ProfilePicServiceImpl;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.vo.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CookMenuListFragment extends Fragment implements UIItemService {

    ListView menuList;
    FloatingActionButton addMenu;
    String ids[]={"-LBV6OZmf8fs7mm0Oiq7","-LBEghqc2TUEUajMCpvq","-LBEh2mBpGksQC1cEbsU","-LBEh8zC0TkyanZ6hMPQ","-LBHG4uwj5RPulChO1Wx"};
    String item_names[]={"Pizza","Pasta","Burger","Salad","Biriyani"};
    String qntys[]={"1","1","3","2","1"};
    String prices[]={"$4.99","$6.99","$5.98","$4.89","$9.80"};
    String avlbltil[]={"04/23/2018 14:35","04/21/2018 14:35","04/20/2018 14:35","04/19/2018 14:35","04/17/2018 14:35"};
    String desc[]={"servers 2, comes with pepperoni, mushrooms, onions, black olives, green peppers toppings","servers 2, comes with pepperoni, mushrooms, onions, black olives, green peppers toppings","servers 2, comes with pepperoni, mushrooms, onions, black olives, green peppers toppings","servers 2, comes with pepperoni, mushrooms, onions, black olives, green peppers toppings","servers 2, comes with pepperoni, mushrooms, onions, black olives, green peppers toppings"};
    int imgs[]={R.drawable.food_default,R.drawable.food_default,R.drawable.food_default,R.drawable.food_default,R.drawable.food_default};
    float ratings[] ={2.0f,3.5f,4f,4.5f,5f};
    List<Item> itemList;
    TextView itemName_txtvw,qnty_txtvw,price_txtvw,availableuntil_txtvw,itemdesc,reviewCount;
    RatingBar item_rating;
    ImageView food_img;

    ItemService itemService = null;
    int itemSize=0;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;





    public CookMenuListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cook_menu_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        menuList = (ListView) getView().findViewById(R.id.menuList);




        itemService = new ItemServiceImpl();
        itemService.readAllforCook(currentUser.getUid(), this);
        addMenu();



        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v,
                                    int position, long id) {

                Intent intent = new Intent(getActivity(), AddMenuActivity.class);
                intent.putExtra("ITEM_ID", itemList.get(position).getItemId());
                Log.d("position passed",position+"");
                Log.d("id passed",ids[position]);

                startActivity(intent);


            }
        });

    }

    public void addMenu(){
        addMenu =(FloatingActionButton)getView().findViewById(R.id.addMenu);
        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),AddMenuActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void displayAllItems(List<Item> items) {
        this.itemList=items;

        CustomAdapter menuFragment = new CustomAdapter(itemList);
        menuList.setAdapter(menuFragment);
    }

    @Override
    public void displayItem(Item item) {


    }

    @Override
    public void displayItemsList(Map<String,Item> items){

    }

    private class CustomAdapter extends BaseAdapter {
        List<Item> itemList;
        ProfilePicService profilePicService=new ProfilePicServiceImpl();
        CustomAdapter(List<Item> itemList){
           this.itemList = itemList;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {



            view = getLayoutInflater().inflate(R.layout.fragment_cook_menu_adapter,null);
            itemName_txtvw = (TextView)view.findViewById(R.id.itemName_txtvw);
            qnty_txtvw = (TextView)view.findViewById(R.id.qnty_txtvw);
            price_txtvw = (TextView)view.findViewById(R.id.price_txtvw);
            availableuntil_txtvw = (TextView)view.findViewById(R.id.availableuntil_txtvw);
            itemdesc = (TextView)view.findViewById(R.id.itemdesc);
            item_rating = (RatingBar)view.findViewById(R.id.item_rating);
            food_img = (ImageView)view.findViewById(R.id.food_img);
            reviewCount =(TextView)view.findViewById(R.id.reviewCount);


            itemName_txtvw.setText(itemList.get(i).getName());
            qnty_txtvw.setText(itemList.get(i).getQuantity()+"");
            price_txtvw.setText(itemList.get(i).getPrice()+"/unit");
            availableuntil_txtvw.setText(itemList.get(i).getDateEnd() + " "+ itemList.get(i).getTimeEnd());
            itemdesc.setText(itemList.get(i).getDescription());
            reviewCount.setText("("+itemList.get(i).getReviews()+")");
            item_rating.setRating(itemList.get(i).getAvgRating());




            if(itemList.get(i).getPhotoPath() == null)
                food_img.setImageResource(R.drawable.food_default);
            else
                profilePicService.loadProfilePic(getActivity(),food_img,itemList.get(i).getPhotoPath());


            return view;
        }
    }


}
