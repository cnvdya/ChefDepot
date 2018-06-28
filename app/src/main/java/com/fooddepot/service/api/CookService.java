package com.fooddepot.service.api;

import com.fooddepot.ui.api.UICookService;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.vo.Cook;
import com.fooddepot.vo.Item;

/**
 * Created by mudrita on 5/3/18.
 */

public interface CookService {
    void add(Cook cook);
    void delete(String id);
    void read(String cookId,UICookService uiCookService);
    void update(Cook cook);
}
