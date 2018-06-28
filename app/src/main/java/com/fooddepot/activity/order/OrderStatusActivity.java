package com.fooddepot.activity.order;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fooddepot.R;

import com.fooddepot.activity.DetailActivity;
import com.fooddepot.exception.ItemException;
import com.fooddepot.service.api.CookService;
import com.fooddepot.service.api.ItemService;
import com.fooddepot.service.api.OrderService;
import com.fooddepot.service.impl.CookServiceImpl;
import com.fooddepot.service.impl.ItemServiceImpl;
import com.fooddepot.service.impl.OrderServiceImpl;
import com.fooddepot.ui.api.UICookService;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.ui.api.UIOrderService;
import com.fooddepot.vo.Cook;
import com.fooddepot.vo.Item;
import com.fooddepot.vo.Order;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public class OrderStatusActivity extends AppCompatActivity implements UIOrderService, UICookService ,UIItemService{


    private String orderID,cookName,cooksAddress,cookPhoneNumber;
    private TextView itemName, itemQuantity, itemPrice, orderDateTime, cookNickName, cookContactNumber, cookAddress, paymentId, orderStatus;
    private Button shareBtn;
    OrderService orderService = null;
    CookService cookService = null;
    ItemService itemService = null;
    Cook cook;
    Order order;
    Item item;
    String cookId;
    private ImageView qrCode;
    boolean callCook=true;
    RatingBar ratingBar;
    private ShareActionProvider mShareActionProvider;
    Bitmap b;
    Intent mShareIntent  = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_status);
        itemName=(TextView)findViewById(R.id.itemName);
        itemQuantity=(TextView)findViewById(R.id.itemQuantity);
        itemPrice=(TextView)findViewById(R.id.itemPrice);
        orderDateTime=(TextView)findViewById(R.id.orderDateTime);
        cookNickName=(TextView)findViewById(R.id.itemSeller);
        cookContactNumber=(TextView)findViewById(R.id.contactNumber);
        cookAddress=(TextView)findViewById(R.id.address);
        paymentId=(TextView)findViewById(R.id.paymentID);
        orderStatus=(TextView)findViewById(R.id.orderStatus);
        qrCode=(ImageView)findViewById(R.id.qrCode);
        ratingBar=(RatingBar)findViewById(R.id.ratingbar);
        shareBtn =(Button)findViewById(R.id.shareBtn);
        orderService = new OrderServiceImpl();
        itemService = new ItemServiceImpl();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.d("ratingbar",""+v);
                Log.d("ratingbar","getRating: "+ratingBar.getRating());


                //save in item object
                //save in cook object
                item.setAvgRating(((item.getAvgRating()*item.getReviews())+ratingBar.getRating())/(item.getReviews()+1));
                item.setReviews(item.getReviews()+1);

                //save in order object
                order.getItem().setAvgRating(ratingBar.getRating());
                order.getItem().setReviews(1);


                try {
                    orderService.updateRating(order, item);
                }catch (ItemException e){
                    e.printStackTrace();
                }
            }
        });



//        qrCode.setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                mShareActionProvider = (ShareActionProvider) item.getActionProvider();
//                if (mShareActionProvider != null ){
//
//                    Intent shareIntent = new Intent();
//                    shareIntent.setAction(Intent.ACTION_SEND);
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(this,b));
//                    shareIntent.setType("image/jpeg");
//
//                    mShareIntent.setAction(Intent.ACTION_SEND);
//                    mShareIntent.setType("text/plain");
//
//                    //mShareIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("key"));
//                    mShareActionProvider.setShareIntent(mShareIntent);
//                return true;
//            }
//        });





        Intent intent = getIntent();
        orderID = intent.getStringExtra("ORDER_ID");
        if(intent.hasExtra("COOK_NAME")) {

            cookName = intent.getStringExtra("COOK_NAME");
            cooksAddress = intent.getStringExtra("COOK_ADDRESS");
            cookPhoneNumber = intent.getStringExtra("COOK_PHONE");

            cookNickName.setText(cookName);
            cookContactNumber.setText(cookPhoneNumber);
            cookAddress.setText(cooksAddress);
            callCook=false;
        }




        orderService.readByOrderID(orderID,this);



        try {
            b = textToImage(orderID, 500, 500);
            qrCode.setImageBitmap(b);



            //b.compress (Bitmap.CompressFormat.JPEG, 100, qrCode.setImageBitmap(textToImage(orderID, 500, 500)));
            //Image img = Image.FromArray (stream.ToArray());
        }catch(Exception e){
            Log.d("Bitmap", "Could not generate bitmap"+ e.getMessage());
        }


        cookAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TextView address = (TextView) findViewById(R.id.address);

                String url = "http://maps.google.com/maps?daddr="+cookAddress.getText().toString();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                startActivity(intent);
            }
        });

        cookContactNumber.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //id of phone text box
                if (ContextCompat.checkSelfPermission(OrderStatusActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//                    TextView editphone = (TextView) findViewById(R.id.contactnumber);
                    Uri phone_number= Uri.parse("tel:" + cookContactNumber.getText().toString());
                    Intent callIntent= new Intent(Intent.ACTION_DIAL);
                    //  startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + PhoneNumber)));
                    callIntent.setData(phone_number);
                    startActivity(callIntent);
                } else
                {
                    Toast.makeText(OrderStatusActivity.this,"Permission denied to make a call",Toast.LENGTH_SHORT).show();
                }


            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(getApplicationContext(),b));
                shareIntent.setType("image/jpeg");
                startActivity(shareIntent);
            }
        });



//        shareBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                mShareActionProvider = (ShareActionProvider) shareBtn.getActionProvider();
////                if (mShareActionProvider != null ){
//
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(getApplicationContext(),b));
//                shareIntent.setType("image/jpeg");
//
//                mShareIntent.setAction(Intent.ACTION_SEND);
//                mShareIntent.setType("text/plain");
//
//                mShareIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("key"));
//                mShareActionProvider.setShareIntent(mShareIntent);
//                //return true;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sharemenu,menu);
//        MenuInflater Inflater = getMenuInflater();
//        Inflater.inflate(R.menu.sharemenu,menu);
        MenuItem item = menu.findItem(R.id.share);
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        if (mShareActionProvider != null ){

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(this,b));
            shareIntent.setType("image/jpeg");

            mShareIntent.setAction(Intent.ACTION_SEND);
            mShareIntent.setType("text/plain");

            //mShareIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("key"));
            mShareActionProvider.setShareIntent(mShareIntent);
        }
        return true;
    }




//    mShareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(item);
//    //create the sharing intent
//    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//      sharingIntent.setType("text/plain");
//    String shareBody = "here goes your share content body";
//      sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Subject");
//     sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//
//    //then set the sharingIntent
//     mShareActionProvider.setShareIntent(sharingIntent);
//            return true;

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        return true;
    }



    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }





    private Bitmap textToImage(String text, int width, int height) throws WriterException, NullPointerException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.DATA_MATRIX.QR_CODE,
                    width, height, null);
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        int colorWhite = 0xFFFFFFFF;
        int colorBlack = 0xFF000000;

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colorBlack : colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, width, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


    @Override
    public void displayAllOrders(List<Order> orders) {

    }

    @Override
    public void displayOrder(Order order) {
        this.order=order;
        itemName.setText(order.getItem().getName());
        itemQuantity.setText(order.getQuantity());
        itemPrice.setText(order.getTotalPrice());
        orderDateTime.setText(order.getOrderDate()+" "+order.getOrderTime());
        cookId=order.getItem().getCookId();
        paymentId.setText("Payment ID:"+order.getPaymentTransactionId());
        orderStatus.setText("Status: "+order.getOrderStatus());
        //ratingBar.setRating(order.getItem().getAvgRating());

        if(callCook) {

            cookService = new CookServiceImpl();
            cookService.read(order.getItem().getCookId(), OrderStatusActivity.this);
        }

        itemService.read(order.getItem().getItemId(),this);

    }

    @Override
    public void displayAllCooks(List<Cook> cook) {

    }

    @Override
    public void displayCook(Cook cook) {
        this.cook=cook;
        cookNickName.setText(cook.getNickName());
        cookContactNumber.setText(cook.getPhoneNumber());
        cookAddress.setText(cook.getAddressLine1()+", "+cook.getAddressLine2()+", "+cook.getState()+", "+cook.getZipcode());

    }

    @Override
    public void displayAllItems(List<Item> items) {

    }

    @Override
    public void displayItem(Item item) {
        this.item=item;

    }

    @Override
    public void displayItemsList(Map<String, Item> items) {

    }
}
