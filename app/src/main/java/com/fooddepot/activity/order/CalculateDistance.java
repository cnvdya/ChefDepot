package com.fooddepot.activity.order;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class CalculateDistance {

    LatLng lmyLocation,ldestLocation;
    double distance;
    String response,parsedDistance;





    public void calculateDistancebetweenpoints(){




        lmyLocation = new LatLng(37.3891453,-122.01008630000001);
        ldestLocation= new LatLng(37.3911238,-121.97293200000001);


//        distance=calculationByDistance(lmyLocation,ldestLocation);
//
//        System.out.println("distance calculated by method: "+distance);
//        distance=distance*0.621371;
//        System.out.println("distance calculated in miles: "+distance);

        String dist=getDistance(37.3891453,-122.01008630000001,37.3911238,-121.97293200000001);
        //distance=Float.parseFloat(dist)*0.621371;
        System.out.println("distance calculated by new function in  miles: "+dist);


    }



    public Barcode.GeoPoint getLocationFromAddress(Context context, String strAddress){

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        Barcode.GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new Barcode.GeoPoint((double) (location.getLatitude()),
                    (double) (location.getLongitude() ));


        }
        catch (IOException ex) {

            ex.printStackTrace();
        }
        return p1;

    }


//    public double calculationByDistance(LatLng StartP, LatLng EndP) {
//        int Radius = 6371;// radius of earth in Km
//        double lat1 = StartP.latitude;
//        double lat2 = EndP.latitude;
//        double lon1 = StartP.longitude;
//        double lon2 = EndP.longitude;
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(Math.toRadians(lat1))
//                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
//                * Math.sin(dLon / 2);
//        double c = 2 * Math.asin(Math.sqrt(a));
//        double valueResult = Radius * c;
//        double km = valueResult / 1;
//        DecimalFormat newFormat = new DecimalFormat("####");
//        int kmInDec = Integer.valueOf(newFormat.format(km));
//        double meter = valueResult % 1000;
//        int meterInDec = Integer.valueOf(newFormat.format(meter));
//        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
//                + " Meter   " + meterInDec);
//
//        return Radius * c;
//    }

    public String getDistance(final double lat1, final double lon1, final double lat2, final double lon2){


        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //URL url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric&mode=driving");



                    URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+lat1+ "," + lon1 + "&destinations=" + lat2 + "," + lon2 + "&key=AIzaSyAzASAMsRtrQzDCJXKroSNJyxFrPSIVG2s");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("rows");
                    JSONObject routes = array.getJSONObject(0);
                    JSONArray legs = routes.getJSONArray("elements");
                    JSONObject steps = legs.getJSONObject(0);
                    JSONObject distance = steps.getJSONObject("distance");
                    parsedDistance=distance.getString("text");


                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parsedDistance;
    }


}
