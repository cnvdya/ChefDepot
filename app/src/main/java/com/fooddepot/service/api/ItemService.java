package com.fooddepot.service.api;

import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.vo.Item;

/**
 * Created by ravisha on 4/26/18.
 */

public interface ItemService {
    void add(Item item);
    void delete(String id);
    void read(String itemId,UIItemService uiItemService);
    void readAllforCook(String cookId,UIItemService uiItemService);
    void readAll(UIItemService uiItemService);
    void update(String itemId,Item item);
    void searchItems(String searchKey,String searchValue,UIItemService uiItemService);

}
