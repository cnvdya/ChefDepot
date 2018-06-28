package com.fooddepot.dao.api;

import com.fooddepot.exception.ItemException;
import com.fooddepot.ui.api.UICookService;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.vo.Cook;
import com.fooddepot.vo.Item;

/**
 * Created by mudrita on 5/3/18.
 */

public interface CookDAO {
    void add(Cook cook) throws ItemException;
    void read(String cookId, final UICookService uiCookService) throws ItemException;
    void delete(String id) throws Exception;
    void update (Cook cook) throws ItemException;
}
