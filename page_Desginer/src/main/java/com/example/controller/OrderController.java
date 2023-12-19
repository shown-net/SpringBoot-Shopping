package com.example.controller;

import com.example.pojo.Order;
import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.service.MailService;
import com.example.service.OrderService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @PostMapping("/order/sendEmail")
    public void sendTextMail(@RequestBody List<Order> orderlist) {
        Integer userID = orderlist.get(0).getUserID();
        User userData = userService.selectEmail(userID);

        String userName = userData.getUserName();
        String to = userData.getEmail();
        String subject = "订单信息";
        String text = "";
        StringBuilder goodslist = new StringBuilder();
        String buyTime = orderlist.get(0).getBuyTime();
        for (Order order : orderlist) {
            goodslist.append(order.getProductName()).append(" ").append(order.getQuantity()).append(" 件\n");
        }
        text = String.format("用户" + userName + "您在" + buyTime + "总共购买了 %s 种商品,分别为:\n"
                + goodslist, orderlist.size());
        mailService.sendTextMailMessage(to, subject, text);
    }

    // 增加新订单信息
    @PostMapping("/order/add")
    public Result add(@RequestBody List<Order> orderList) {
        orderService.add_Order(orderList);
        return Result.success("增加订单信息成功");
    }

    // 查询用户订单信息
    @PostMapping("/order/selectInfoByUserId")
    public Result infoKind(@RequestBody User user) {
        List<Order> order_List = orderService.getInfoBy_userId(user);
        if (order_List != null) {
            return Result.success("查询用户订单信息成功", order_List);
        } else {
            return Result.error("用户数据错误，查询失败", null);
        }

    }

    // 查询全部订单统计信息
    @PostMapping("/order/selectInfoStatistics")
    public Result infoStatistics() {
        List<Order> order_List = orderService.getStatistics();
        if (order_List != null) {
            return Result.success("查询订单统计信息成功", order_List);
        } else {
            return Result.error("查询失败", null);
        }

    }

    // 删除用户订单信息
    @PostMapping("/order/deleteById")
    public Result deleteOrder(@RequestBody List<String> ID_List) {
        orderService.delete_Order(ID_List);
        return Result.success("删除用户订单成功", null);
    }
}
