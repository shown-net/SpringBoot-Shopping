package com.example.mapper;

import com.example.pojo.Order;
import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    //插入新增的订单信息
    @Insert("insert into orders (user_id,product_ID,product_Name,price,quantity,buy_time) " +
            " values(#{userID},#{productID},#{productName},#{price},#{quantity},#{buyTime})")
    public void add_Order(Order orderItem);

    //根据提供的用户id返回用户订单数据 --数据字段起别名
    public List<Order> orderInfoSelect(User user);

    //返回订单统计数据 --数据字段起别名
    public List<Order> orderStatisticsSelect();

    //更新订单信息，删除用户已删除订单
    public void deleteOrder(List<Integer> Id_List);

}
