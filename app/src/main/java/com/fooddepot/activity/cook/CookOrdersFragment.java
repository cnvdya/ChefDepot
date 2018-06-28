package com.fooddepot.activity.cook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.fooddepot.BlankFragment;
import com.fooddepot.R;
import com.fooddepot.service.api.OrderService;
import com.fooddepot.service.impl.OrderServiceImpl;
import com.fooddepot.storage.api.ProfilePicService;
import com.fooddepot.storage.impl.ProfilePicServiceImpl;
import com.fooddepot.ui.api.UIOrderService;
import com.fooddepot.vo.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CookOrdersFragment extends Fragment implements UIOrderService {
    ListView orderList;

    int imgs[]={R.drawable.profile_pic,R.drawable.profile_pic,R.drawable.profile_pic,R.drawable.profile_pic,R.drawable.profile_pic};


    List<Order> orders;
    OrderService orderService;
    Order order;
    private static final String TAG="CookOrdersFragment";


    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;



    public CookOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cook_orders, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        orderService = new OrderServiceImpl();
        Log.d(TAG,"cook id :"+currentUser.getUid().toString());
        orderService.readByCook(currentUser.getUid().toString(), this);

        orderList = (ListView) getView().findViewById(R.id.orderList);



    }

    @Override
    public void displayAllOrders(List<Order> orders) {
        this.orders=orders;

        CustomAdapter blankFragment = new CustomAdapter(orders);
        orderList.setAdapter(blankFragment);
    }

    @Override
    public void displayOrder(Order order) {

    }

    private class CustomAdapter extends BaseAdapter{

        List<Order> orders;
        ProfilePicService profilePicService =new ProfilePicServiceImpl();
        CustomAdapter(List<Order> orders){
           this.orders = orders;
        }

        @Override
        public int getCount() {
            return orders.size();
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.fragment_order_adapter,null);
                TextView customer_name = (TextView)view.findViewById(R.id.name_txtvw);
                TextView phone_txtvw = (TextView)view.findViewById(R.id.phone_txtvw);
                TextView datetime_txtvw = (TextView)view.findViewById(R.id.datetime_txtvw);
                TextView itemName = (TextView)view.findViewById(R.id.itemName);
                TextView qnty = (TextView)view.findViewById(R.id.qnty);
                TextView price = (TextView)view.findViewById(R.id.price);
                final Spinner order_status = (Spinner)view.findViewById(R.id.order_status);
                ImageView customer_img = (ImageView)view.findViewById(R.id.customer_img);

                Button closeOrder = (Button)view.findViewById(R.id.closeOrder);

                final Order order = orders.get(i);

                customer_name.setText(orders.get(i).getUserName());
                phone_txtvw.setText(orders.get(i).getUserPhone());
                datetime_txtvw.setText(orders.get(i).getOrderDate()+" "+orders.get(i).getOrderTime());
                itemName.setText(orders.get(i).getItem().getName());
                qnty.setText(orders.get(i).getQuantity());
                price.setText("$"+orders.get(i).getTotalPrice());



            if(orders.get(i).getUserProfilePic()==null)
                customer_img.setImageResource(R.drawable.profile_pic);
            else
                profilePicService.loadProfilePic(getActivity(),customer_img,orders.get(i).getUserProfilePic());



            //customer_img.setImageResource(R.drawable.profile_pic);

            closeOrder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), VerifyQRActivity.class);
                    intent.putExtra("ORDER_ID", orders.get(i).getOrderId());
                    startActivity(intent);
                }
            });

            String orderStatus[]=getResources().getStringArray(R.array.order_status);
            int catPos=0;
            for(int t=0; t < orderStatus.length; t++)
                if(orderStatus[t].equals(order.getOrderStatus()))
                    catPos = t;

            order_status.setSelection(catPos);
            order_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                    Log.d("FoodDepot",order_status.getSelectedItem().toString());

                    Log.d("FoodDepot",order.getOrderStatus());
                    if(!order_status.getSelectedItem().toString().equals(order.getOrderStatus())){
                        order.setOrderStatus(order_status.getSelectedItem().toString());
                        orderService.update(order);}
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            return view;
        }
    }


}
