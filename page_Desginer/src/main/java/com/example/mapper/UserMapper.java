package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //检查是否有重复用户名，返回重复的数量
    @Select("select count(id) from user where userName=#{userName}")
    public Integer userCount(String userName);

    //插入新增的注册用户信息
    @Insert("insert into user (userName, account_type,password, email) " +
            "values(#{userName},#{accountType},#{password},#{email})")
    public void add_User(User user);

    //查询该用户名是否存在且密码和账户类型是否匹配，返回Id列表
    @Select("select id from user where account_type=#{accountType} " +
            "and userName=#{userName} and password=#{password} ")
    public List<Integer> userCheck(User user);

    //根据提供的用户名返回用户数据
    @Select("select * from user where userName=#{userName}")
    public User userInfoSelect(String userName);

    @Select("select userName,email from user where id=#{userID}")
    public User userEmailSelect(Integer userID);

    //更新用户信息(根据用户ID)
    @Update("update user set password=#{password}," +
            "gender=#{gender},age=#{age},email=#{email},phoneNumber=#{phoneNumber} " +
            "where id=#{id}")
    public void InfoUpdate(User user);

    //注销用户信息
    @Delete("delete from user where id=#{id}")
    public void LogOff(Integer id);
}
