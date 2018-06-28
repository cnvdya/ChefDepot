package com.fooddepot.dao.api;

import com.fooddepot.exception.ItemException;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.ui.api.UIUserService;
import com.fooddepot.vo.User;

/**
 * Created by mudrita on 5/3/18.
 */

public interface UserDAO {
    void add(User user) throws ItemException;
    void read(String userID,UIUserService uiUserService) throws ItemException;
    void delete(String userID) throws Exception;
    void update (User user) throws ItemException;

}
