package com.example.service;


import com.example.pojo.Product;

import java.util.List;

public interface ProductService {

    Boolean getBy_Productname(Product product);


    Product getInfoBy_name(String name);

    List<Product> getInfoBy_kind(List<String> kindList);

    void UpdateInfoBy_ID(Product product);

    void deleteProductBy_ID(Integer ID);

}
