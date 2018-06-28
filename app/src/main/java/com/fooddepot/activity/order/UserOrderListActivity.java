package com.fooddepot.activity.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fooddepot.R;
import com.fooddepot.activity.cook.AddMenuActivity;
import com.fooddepot.activity.cook.CookMenuListFragment;
import com.fooddepot.service.api.OrderService;
import com.fooddepot.service.impl.OrderServiceImpl;
import com.fooddepot.service.impl.UserServiceImpl;
import com.fooddepot.storage.api.ProfilePicService;
import com.fooddepot.storage.impl.ProfilePicServiceImpl;
import com.fooddepot.ui.api.UIOrderService;
import com.fooddepot.vo.Item;
import com.fooddepot.vo.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserOrderListActivity extends AppCompatActivity implements UIOrderService{

    private ListView orderList;
    TextView itemName_txtvw,qnty_txtvw,price_txtvw,availableuntil_txtvw,itemdesc,reviewCount;
    RatingBar item_rating;
    ImageView food_img;
    OrderService orderService;
    List<Order> orders;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_list);

        orderService=new OrderServiceImpl();
        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        orderService.readByUser(currentUser.getUid(),this);
        orderList = (ListView)findViewById(R.id.orderList);



        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v,
                                    int position, long id) {

                Intent intent = new Intent(UserOrderListActivity.this, OrderStatusActivity.class);
                intent.putExtra("ORDER_ID", orders.get(position).getOrderId());
                startActivity(intent);


            }
        });
    }

    @Override
    public void displayAllOrders(List<Order> orders) {
        this.orders=orders;
        CustomAdapter menuFragment = new CustomAdapter(orders);
        orderList.setAdapter(menuFragment);

    }

    @Override
    public void displayOrder(Order order) {

    }


    private class CustomAdapter extends BaseAdapter {
        List<Order> orderList;
        ProfilePicService profilePicService=new ProfilePicServiceImpl();
        CustomAdapter(List<Order> orderList){
            this.orderList = orderList;
        }

        @Override
        public int getCount() {
            return orderList.size();
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


            itemName_txtvw.setText(orderList.get(i).getItem().getName());
            qnty_txtvw.setText(orderList.get(i).getQuantity()+"");
            price_txtvw.setText("$"+orderList.get(i).getTotalPrice());
            availableuntil_txtvw.setText(orderList.get(i).getOrderDate() + " "+ orderList.get(i).getOrderTime());
            itemdesc.setText(orderList.get(i).getOrderStatus());
            item_rating.setRating(orderList.get(i).getItem().getAvgRating());
            reviewCount.setText("("+orderList.get(i).getItem().getReviews()+")");
            //food_img.setImageResource(R.drawable.food_default);


            if(orderList.get(i).getItem().getPhotoPath() == null)
                food_img.setImageResource(R.drawable.food_default);
            else
                profilePicService.loadProfilePic(UserOrderListActivity.this,food_img,orderList.get(i).getItem().getPhotoPath());



            return view;
        }


    }
}
