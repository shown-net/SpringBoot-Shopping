package com.example.service.ipml;

import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean getBy_userName(User user) {
        //先查询重复用户名的数量
        Integer count = userMapper.userCount(user.getUserName());
        //不存在重复用户，执行插入操作
        if (count == 0) {
            userMapper.add_User(user);
            return Boolean.TRUE;
        }
        //返回错误bool值
        else {
            return Boolean.FALSE;
        }
    }

    @Override
    public List<Integer> getBy_userName_Password(User user) {
        return userMapper.userCheck(user);
    }

    @Override
    public User getInfoBy_userName(String userName) {
        return userMapper.userInfoSelect(userName);
    }

    @Override
    public Boolean UpdateInfoBy_userName(User user) {
        userMapper.InfoUpdate(user);
        return Boolean.TRUE;
    }

    @Override
    public Boolean DeleteUserInfo(Integer ID) {
        try {
            userMapper.LogOff(ID);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public User selectEmail(Integer userID) {
        return userMapper.userEmailSelect(userID);
    }
}
