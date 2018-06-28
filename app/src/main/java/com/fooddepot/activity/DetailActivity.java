package com.fooddepot.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.fooddepot.R;
import com.fooddepot.activity.cook.AddMenuActivity;
import com.fooddepot.activity.order.OrderStatusActivity;
import com.fooddepot.service.api.CookService;
import com.fooddepot.service.api.ItemService;
import com.fooddepot.service.api.OrderService;
import com.fooddepot.service.api.UserService;
import com.fooddepot.service.impl.CookServiceImpl;
import com.fooddepot.service.impl.ItemServiceImpl;
import com.fooddepot.service.impl.OrderServiceImpl;
import com.fooddepot.service.impl.UserServiceImpl;
import com.fooddepot.storage.impl.ProfilePicServiceImpl;
import com.fooddepot.ui.api.UICookService;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.ui.api.UIOrderService;
import com.fooddepot.ui.api.UIUserService;
import com.fooddepot.util.DAOUtil;
import com.fooddepot.vo.Cook;
import com.fooddepot.vo.Item;
import com.fooddepot.vo.Order;
import com.fooddepot.vo.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ProofOfPayment;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements UIItemService,UICookService,UIUserService {

    private TextView tvName, tvDescription, tvPrice,address,contactnumber;
    private ImageView itemImage;
    private Button buy;
    private Spinner spinner;
    ItemService itemService = null;
    CookService cookService = null;
    OrderService orderService = null;
    UserService userService=null;
    String itemID,cookID,cookAddress,cookPhoneNumber,cookNickName;
    Item item;
    float totalPrice;
    private User user;
    ProfilePicServiceImpl profilePicService;

    private final String CLIENT_ID ="AWiEg-My1duflzQ5etCCtq85otw_PpH_cHfgXMENIHVaWMq-ArmkpvS1H5hzMmLjcMxHX7jr4SiCAz69";
    private final String TAG="DetailActivity";


    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        tvName = (TextView) findViewById(R.id.tvItemName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        itemImage = (ImageView) findViewById(R.id.itemImage);
        address=(TextView)findViewById(R.id.address);
        contactnumber=(TextView)findViewById(R.id.contactnumber);
        buy=(Button)findViewById(R.id.buy);
        spinner=(Spinner) findViewById(R.id.spinner);

        orderService=new OrderServiceImpl();
        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userService=new UserServiceImpl();
        userService.read(currentUser.getUid(),this);
        profilePicService = new ProfilePicServiceImpl();
//        tvName.setText("CheeseCake");
//        tvDescription.setText("As the name implies, the recipe for Junior's famous original cheesecake" +
//                " has been baked the very same way since the 1950s. And for good reason. It's simply " +
//                "The Best cheesecake you can find. \"There will never be a better cheesecake than the cheesecake they serve at " +
//                "Junior's on Flatbush Avenue... it's the best ... ");

        ImageView getdirection = (ImageView) findViewById(R.id.drivingdirection) ;
        getdirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView address = (TextView) findViewById(R.id.address);

                String url = "http://maps.google.com/maps?daddr="+address.getText().toString();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                startActivity(intent);
            }
        });


        //id of RING image
        ImageView callImg = (ImageView) findViewById(R.id.contacticon);
        callImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //id of phone text box
                if (ContextCompat.checkSelfPermission(DetailActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    TextView editphone = (TextView) findViewById(R.id.contactnumber);
                    Uri phone_number= Uri.parse("tel:" + editphone.getText().toString());
                    Intent callIntent= new Intent(Intent.ACTION_DIAL);
                    //  startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + PhoneNumber)));
                    callIntent.setData(phone_number);
                    startActivity(callIntent);
                } else
                {
                    Toast.makeText(DetailActivity.this,"Permission denied to make a call",Toast.LENGTH_SHORT).show();
                }


            }
        });


        Intent intent = getIntent();
        if(intent.hasExtra("ITEM_ID")){
            itemService = new ItemServiceImpl();
            cookService =new CookServiceImpl() ;
            itemID= intent.getStringExtra("ITEM_ID");
            cookID=intent.getStringExtra("COOK_ID");
            Log.d("Id received",itemID);
            Log.d("cookId received",cookID);
            itemService.read(itemID, this);
            cookService.read(cookID,this);

        }

    }

    public void onBuy(View view){

        totalPrice = Integer.parseInt(spinner.getSelectedItem().toString())*item.getPrice();







        //Paypal code

        System.out.println("Button Clicked success");


        PayPalConfiguration config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(CLIENT_ID);
        Log.d(TAG,"Do sale!!!");

        if(totalPrice>0) {

            PayPalPayment payment = new PayPalPayment(new BigDecimal(totalPrice), "USD", "Order Price",
                    PayPalPayment.PAYMENT_INTENT_SALE);

            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);

            // send the same configuration for restart resiliency
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

            startActivityForResult(intent, 1);
        }

        else{

            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String orderDate = formatter.format(date);
            formatter= new SimpleDateFormat("HH:mm");
            String orderTime = formatter.format(date);
            String orderId = DAOUtil.getDatabaseReference().push().getKey();
            Order order = new Order(getResources().getStringArray(R.array.order_status)[0],orderDate,
                    orderTime,spinner.getSelectedItem()+"",totalPrice+"",Boolean.TRUE,item,
                    user.getUid(),user.getName(),user.getPhoneNumber(),"Charity_"+orderId);

            order.setOrderId(orderId);
            if(user.getProfilePicPath()!=null){
                order.setUserProfilePic(user.getProfilePicPath());
            }
            orderService.add(order);

            Log.d(TAG,"starting new intent");

            Intent intent = new Intent(this, OrderStatusActivity.class);
            intent.putExtra("ORDER_ID", orderId);
            intent.putExtra("COOK_NAME", cookNickName);
            intent.putExtra("COOK_ADDRESS", cookAddress);
            intent.putExtra("COOK_PHONE", cookPhoneNumber);

            startActivity(intent);
            Log.d(TAG,"starting new intent last line");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String payMentStatus = "cancelled";
        String transactionId = "";

        super.onActivityResult(requestCode, resultCode, data);
        PaymentConfirmation confirm =
                data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

        if(confirm!=null){
            ProofOfPayment proofOfPayment = confirm.getProofOfPayment();
            Log.d("proofofpaymentjson",proofOfPayment.toJSONObject().toString() );
            try {
                payMentStatus = proofOfPayment.toJSONObject().getString("state");
                transactionId = proofOfPayment.toJSONObject().getString("id");

            } catch (JSONException e) {
                Log.d("Order Payment Status", "Payment failed "+ e.getLocalizedMessage());
            }



        }else{
            Log.d("Payment Transaction Id", "Confirm payment is null");
        }

        if("approved".equalsIgnoreCase(payMentStatus)){

            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String orderDate = formatter.format(date);
            formatter= new SimpleDateFormat("HH:mm");
            String orderTime = formatter.format(date);

            Order order = new Order(getResources().getStringArray(R.array.order_status)[0],orderDate,
                    orderTime,spinner.getSelectedItem()+"",totalPrice+"",Boolean.TRUE,item,
                    user.getUid(),user.getName(),user.getPhoneNumber(),transactionId);
            String orderId = DAOUtil.getDatabaseReference().push().getKey();
            order.setOrderId(orderId);
            if(user.getProfilePicPath()!=null){
                order.setUserProfilePic(user.getProfilePicPath());
            }
            orderService.add(order);

            Log.d(TAG,"starting new intent");

            Intent intent = new Intent(this, OrderStatusActivity.class);
            intent.putExtra("ORDER_ID", orderId);
            intent.putExtra("COOK_NAME", cookNickName);
            intent.putExtra("COOK_ADDRESS", cookAddress);
            intent.putExtra("COOK_PHONE", cookPhoneNumber);

            startActivity(intent);
            Log.d(TAG,"starting new intent last line");

        }


        Log.d("transactionId", transactionId);

    }

    @Override
    public void displayAllItems(List<Item> items) {

    }

    @Override
    public void displayItem(Item item) {
        this.item=item;
        tvName.setText(item.getName());
        tvPrice.setText("$"+item.getPrice()+"/unit");
        tvDescription.setText(item.getDescription());
        //itemImage.setImageResource(item.getPhotoPath());


        if(item.getPhotoPath()==null)
            itemImage.setImageResource(R.drawable.food_default);
        else
            profilePicService.loadProfilePic(this,itemImage,item.getPhotoPath());

    }

    @Override
    public void displayItemsList(Map<String, Item> items) {

    }

    @Override
    public void displayAllCooks(List<Cook> cook) {

    }

    @Override
    public void displayCook(Cook cook) {
        cookAddress= cook.getAddressLine1()+", "+cook.getAddressLine2()+", "+cook.getState()+", "+cook.getCountry()+", "+cook.getZipcode();
        cookPhoneNumber=cook.getPhoneNumber();
        cookNickName=cook.getNickName();
        address.setText(cookAddress);
        contactnumber.setText(cookPhoneNumber);

    }

    @Override
    public void displayAllUsers(List<User> user) {

    }

    @Override
    public void displayUser(User user) {
        this.user=user;

    }
}
