package com.example.service;


import com.example.pojo.Result;
import com.example.pojo.User;

import java.util.List;

public interface UserService {

    Boolean getBy_userName(User user);

    List<Integer> getBy_userName_Password(User user);

    User getInfoBy_userName(String userName);

    Boolean UpdateInfoBy_userName(User user);

    Boolean DeleteUserInfo(Integer ID);

    User selectEmail(Integer userID);
}
