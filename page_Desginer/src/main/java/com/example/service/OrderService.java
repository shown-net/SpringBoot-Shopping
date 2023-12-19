package com.example.service;


import com.example.pojo.Order;
import com.example.pojo.User;

import java.util.List;

public interface OrderService {

   void add_Order(List<Order> Order);

    List<Order> getInfoBy_userId(User user);

    List<Order> getStatistics();
    void delete_Order(List<String>id_List);
}
