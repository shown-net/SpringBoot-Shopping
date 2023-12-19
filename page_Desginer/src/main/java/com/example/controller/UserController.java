package com.example.controller;

import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
//@RequestMapping("/sign")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    // 增加注册用户
    @PostMapping("/user/register")
    public Result register(@RequestBody User user) {
        Boolean result = userService.getBy_userName(user);
        if (result == Boolean.TRUE) {
            return Result.success("注册账号成功", null);
        } else {
            return Result.error("存在重复用户名，请重试", null);
        }
    }

    // 用户登录信息查询
    @PostMapping("/user/login")
    public Result login(@RequestBody User user) {
        List<Integer> id_List = userService.getBy_userName_Password(user);
        //返回的账号只有一个,生成令牌
        if (id_List.size() == 1) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userName", user.getUserName());
            String jwt = JwtUtils.generateJwt(claims);
            // 返回用户Id
            return Result.success(id_List.get(0).toString(), jwt);
        }
        //返回错误信息
        else {
            return Result.error("用户名或者密码错误", null);
        }
    }

    // 查询用户个人信息
    @PostMapping("/user/selectInfo")
    public Result info(@RequestBody String userName) {
        //去除双引号
        User user = userService.getInfoBy_userName(userName.replace("\"", ""));
        return Result.success("查询用户信息成功", user);
    }

    // 更新用户个人信息
    @PostMapping("/user/updateInfo")
    public Result updateInfo(@RequestBody User user) {
        Boolean result = userService.UpdateInfoBy_userName(user);
        if (result == Boolean.TRUE) {
            return Result.success("更新账号信息成功", null);
        } else {
            return Result.error("未知错误，请重试", null);
        }
    }

    // 删除用户信息
    @PostMapping("/user/LogOff")
    public Result deleteInfo(@RequestBody Integer ID) {
        Boolean result = userService.DeleteUserInfo(ID);
        if (result == Boolean.TRUE) {
            return Result.success("注销成功", null);
        } else {
            return Result.error("注销失败", null);
        }

    }
}
