package com.example.mapper;

import com.example.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    //检查是否有重复商品名，返回重复的数量
    @Select("select count(id) from product where name=#{name}")
    public Integer NameCount(String name);

    //插入新增的商品信息
    @Insert("insert into product (manager_id,kind,name,price,image_Url) " +
            "values(#{managerID},#{kind},#{name},#{price},#{imageUrl})")
    public void add_Product(Product product);

    //根据提供的商品名返回商品数据
    @Select("select * from product where name=#{name}")
    public Product InfoSelect(String name);

    //消费者视角返回所有商品信息
    public List<Product> InfoKindSelect(List<String> kindList);

    //更新商品信息(根据商品ID)
    @Update("update product set name=#{name},kind=#{kind},price=#{price}  " +
            " where id=#{id}")
    public void InfoUpdate(Product product);

    //移除商品信息(根据商品ID)
    @Delete("delete from product where id=#{ID}")
    public void deleteProduct(Integer ID);

}
