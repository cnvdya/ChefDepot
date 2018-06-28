package com.fooddepot.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fooddepot.R;
import com.fooddepot.activity.order.CalculateDistance;
import com.fooddepot.service.api.ItemService;
import com.fooddepot.service.impl.ItemServiceImpl;
import com.fooddepot.storage.api.ProfilePicService;
import com.fooddepot.storage.impl.ProfilePicServiceImpl;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.vo.Item;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SimpleTimeZone;
import static android.content.Context.LOCATION_SERVICE;
import android.location.LocationListener;



public class SearchFragment extends Fragment implements UIItemService,SearchView.OnQueryTextListener {
    public static final String ITEM_NAME = "EXTRA_COUNTRY";
    public static final String IMAGE_NAME = "IMAGE_NAME";
    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    private LocationManager locationManager;
    private LocationListener listener;
    double currentLatitude,currentLogitude;

    private SearchView search_text;
    ItemService itemService = null;
    List<Item> itemList;
    ListView listView;
    ImageView imageView;
    TextView address,distancetext,nameTxt,itemTxt,itemPriceTxt,reviewCount;
    CalculateDistance calculateDistance;
    RatingBar ratingbar;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menusearch, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView)item.getActionView();

        //SearchView searchView = new SearchView(((OrderActivity) mContext).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        itemService=new ItemServiceImpl();
        itemService.readAll(this);

        calculateDistance = new CalculateDistance();
//        calculateDistance.calculateDistancebetweenpoints();
//        Barcode.GeoPoint geoPoint= calculateDistance.getLocationFromAddress(getContext(),"3000 Mission College Blvd Santa Clara CA 95054");
//        Log.d("SearchFragment","GeoPoint Latitude"+geoPoint.lat/ 1E6 );
//        Log.d("SearchFragment","GeoPoint Logitude"+geoPoint.lng/1E6);
//        double destLatitude=geoPoint.lat/ 1E6;
//        double destLongitude=geoPoint.lng/1E6;
        locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLatitude=location.getLongitude();
                currentLogitude=location.getLatitude();
                Log.d("SearchFragment","currentLatitude"+currentLatitude);
                Log.d("SearchFragment","currentLogitude"+currentLogitude);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

//        String dist=calculateDistance.getDistance(currentLatitude,currentLogitude,destLatitude,destLongitude);
//        Log.d("SearchFragment","distance found"+dist);




        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                            ,10);
                }
                return;
            }
            else{

                    locationManager.requestLocationUpdates("gps", 60000, 0, listener);
                }



    }


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

//        currentLatitude = getArguments().getDouble("LATITUDE");
//        currentLogitude = getArguments().getDouble("LONGITUDE");

        String[] menuItems = {"Apple Pie", "cheesecake", "Classic Burger", "Chef Salad"};

        int[] images = {R.drawable.apple_pie, R.drawable.cheesecake, R.drawable.classic_burger, R.drawable.chef_salad};
        HashMap<String, String> map = new HashMap<String, String>();

        for (int i = 0; i < menuItems.length; i++) {
            map = new HashMap<String, String>();
            map.put("Menu", menuItems[i]);
            map.put("Image", Integer.toString(images[i]));

            data.add(map);
        }

        String[] from = {"Menu", "Image", "Price", "Address", "phone", "description"};
        int[] to = {R.id.nameTxt, R.id.imageView};


        listView = (ListView) view.findViewById(R.id.restaurantList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v,
                                    int position, long id) {
                //Toast.makeText(getActivity(), data.get(position).get("Menu"), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("ITEM_ID", itemList.get(position).getItemId());
                intent.putExtra("COOK_ID", itemList.get(position).getCookId());
                Log.d("position passed",position+"");
                Log.d("id passed",itemList.get(position).getItemId());
                Log.d("cook id passed",itemList.get(position).getCookId());

                startActivity(intent);


            }
        });

        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search_text = (SearchView)view.findViewById(R.id.search_text);
        search_text.setOnQueryTextListener(this);
        search_text.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               System.out.println("search text");
                                           }
                                       }
        );



//        Intent intent = getIntent();
    }


    @Override
    public void displayAllItems(List<Item> items) {
        this.itemList=items;
        for(Item item:items){


//            Barcode.GeoPoint geoPoint= calculateDistance.getLocationFromAddress(getContext(),item.getAddress());
//            Log.d("SearchFragment","Address"+item.getAddress() );
//            Log.d("SearchFragment","GeoPoint Latitude"+geoPoint.lat );
//            Log.d("SearchFragment","GeoPoint Logitude"+geoPoint.lng);
//            double destLatitude=geoPoint.lat;
//            double destLongitude=geoPoint.lng;
//            item.setLatitude(destLatitude);
//            item.setLongitude(destLongitude);
            currentLatitude=37.392033;
            currentLogitude=-121.983114;
            String dist=calculateDistance.getDistance(currentLatitude,currentLogitude,item.getLatitude(),item.getLongitude());
            Log.d("SearchFragment","distance found"+dist);
            item.setDistaceCalc(dist);

            Log.d("SearchFragmentCurntLat",currentLatitude+"");
            Log.d("SearchFragmentCurntLng",currentLatitude+"");
            Log.d("Item in search: name: ",item.getName());
            Log.d("Item in search: id: ",item.getItemId());
            Log.d("Item in search: cookid:",item.getCookId());
        }

        CustomAdapter itemFragment = new CustomAdapter(itemList);
        listView.setAdapter(itemFragment);



    }

    @Override
    public void displayItem(Item item) {

    }

    @Override
    public void displayItemsList(Map<String, Item> items) {
      System.out.println("search return value"+items);
       List<Item> itemList = new ArrayList<Item>(items.values());

       /* CustomAdapter(List itemList){
            this.itemList = itemList;
        }*/

        Set<String> keys = items.keySet();
        for (String key:keys){
            System.out.println(key);
            System.out.println(items.get(key).getName());
        }
       System.out.println("search return value"+items.size());
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
    ItemService is = new ItemServiceImpl();
    is.searchItems("name",s,this);
       // System.out.println("inside onquery text submit");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //System.out.println("inside onquery text change");
        return false;
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



            view = getLayoutInflater().inflate(R.layout.model,null);

            imageView = (ImageView)view.findViewById(R.id.imageView);
            address=(TextView)view.findViewById(R.id.address);
            distancetext = (TextView)view.findViewById(R.id.distancetext);
            nameTxt = (TextView)view.findViewById(R.id.nameTxt);
            itemTxt= (TextView)view.findViewById(R.id.itemTxt);
            itemPriceTxt=(TextView)view.findViewById(R.id.itemPriceTxt);
            ratingbar=(RatingBar) view.findViewById(R.id.rating);
            reviewCount=(TextView)view.findViewById(R.id.reviewCount);


            address.setText(itemList.get(i).getAddress());
            nameTxt.setText(itemList.get(i).getNickName());
            itemTxt.setText(itemList.get(i).getName());
            itemPriceTxt.setText("$"+itemList.get(i).getPrice());
            ratingbar.setRating(itemList.get(i).getAvgRating());
            reviewCount.setText("("+itemList.get(i).getReviews()+")");
           // searchEditText = (EditText) view.findViewById(R.id.search_text);

            distancetext.setText(itemList.get(i).getDistaceCalc());

            if(itemList.get(i).getPhotoPath() == null)
                imageView.setImageResource(R.drawable.food_default);
            else
                profilePicService.loadProfilePic(getActivity(),imageView,itemList.get(i).getPhotoPath());


            return view;
        }
    }
}