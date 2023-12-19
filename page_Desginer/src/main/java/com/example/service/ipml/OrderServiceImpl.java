package com.example.service.ipml;

import com.alibaba.fastjson.JSON;
import com.example.mapper.OrderMapper;
import com.example.pojo.Order;
import com.example.pojo.User;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.ToIntFunction;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void add_Order(List<Order> orderList) {
        orderList.forEach(orderItem -> {
            //执行订单插入操作
            orderMapper.add_Order(orderItem);
        });

    }

    @Override
    public List<Order> getInfoBy_userId(User user) {
        return orderMapper.orderInfoSelect(user);
    }

    @Override
    public List<Order> getStatistics() {
        return orderMapper.orderStatisticsSelect();
    }

    @Override
    public void delete_Order(List<String> id_List) {
        List<Integer> new_IDList = new ArrayList<>(id_List.size());
        for (String id : id_List) {
            new_IDList.add(Integer.valueOf(id));
        }
        orderMapper.deleteOrder(new_IDList);
    }

}
