package com.fooddepot.service.api;

import com.fooddepot.ui.api.UIUserService;
import com.fooddepot.vo.Item;
import com.fooddepot.vo.User;

public interface UserService {
    void add(User user);
    void delete(String id);
    void read(String userId,UIUserService uiUserService);
    void readForCook(String userId,UIUserService uiUserService);
    void readAll(UIUserService uiItemService);
    void update(User user);

}
