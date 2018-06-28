
package com.fooddepot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.fooddepot.R;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

public class FilterFragment extends Fragment {
    SeekBar seekBar;

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //RangeSeekBar rangeSeekBar = (RangeSeekBar) getView().findViewById(R.id.rangeSeekBar);
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(getActivity());

        rangeSeekBar.setRangeValues(0, 100);
        // rangeSeekBar.setSelectedMinValue(20);
        // rangeSeekBar.setSelectedMaxValue(88);
        // Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
        // Add to layout
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //Now you have the minValue and maxValue of your RangeSeekbar
                Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), minValue + "-" + maxValue, Toast.LENGTH_LONG).show();
            }
        });

        // Get noticed while dragging
        rangeSeekBar.setNotifyWhileDragging(true);
        //  RangeSeekBar rangeSeekBar = (RangeSeekBar) getView().findViewById(R.id.rangeSeekBar);
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }


    // @Override
    //public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
}







