package com.fooddepot.activity.cook;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fooddepot.R;
import com.fooddepot.service.api.OrderService;
import com.fooddepot.service.impl.ItemServiceImpl;
import com.fooddepot.service.impl.OrderServiceImpl;
import com.fooddepot.ui.api.UIOrderService;
import com.fooddepot.vo.Order;
import com.google.zxing.Result;

import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class VerifyQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, UIOrderService {

    private ZXingScannerView mScannerView;
    private String orderID=null;
    OrderService orderService = null;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_qr);

        // Programmatically initialize the scanner view
        mScannerView = new ZXingScannerView(this);
        // Set the scanner view as the content view
        setContentView(mScannerView);
        orderService = new OrderServiceImpl();

        Intent intent = getIntent();
        if(intent.hasExtra("ORDER_ID")){
            orderID = intent.getStringExtra("ORDER_ID");
            orderService.readByOrderID(orderID,this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        // Prints scan results
        Log.d("result", rawResult.getText());
        if(rawResult.getText().equals(orderID)){
            Log.d("result","Result Matched");
            order.setOrderStatus("Delivered");
            orderService.update(order);
            Toast.makeText(getBaseContext(), "Order Status updated Successfully as Delivered", Toast.LENGTH_LONG).show();
        }
        // Prints the scan format (qrcode, pdf417 etc.)
        Log.d("result", rawResult.getBarcodeFormat().toString());
        //If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        //intent.putExtra(AppConstants.KEY_QR_CODE, rawResult.getText());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void displayAllOrders(List<Order> orders) {

    }

    @Override
    public void displayOrder(Order order) {
        this.order=order;


    }
}
