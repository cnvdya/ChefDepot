package com.fooddepot.ui.api;

import com.fooddepot.vo.Cook;

import java.util.List;

/**
 * Created by mudrita on 5/3/18.
 */

public interface UICookService {
    void displayAllCooks(List<Cook> cook);
    void displayCook(Cook cook);
}
