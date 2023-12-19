package com.example.service.ipml;

import com.example.mapper.ProductMapper;
import com.example.pojo.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Boolean getBy_Productname(Product product) {
        //先查询重复商品名的数量
        Integer count = productMapper.NameCount(product.getName());
        //不存在重复商品名，执行插入操作
        if (count == 0) {
            productMapper.add_Product(product);
            return Boolean.TRUE;
        }
        //返回错误bool值
        else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Product getInfoBy_name(String username) {
        return productMapper.InfoSelect(username);
    }

    @Override
    public List<Product> getInfoBy_kind(List<String> kindList) {
        return productMapper.InfoKindSelect(kindList);
    }

    @Override
    public void UpdateInfoBy_ID(Product product) {
        productMapper.InfoUpdate(product);
    }

    @Override
    public void deleteProductBy_ID(Integer ID) {
        productMapper.deleteProduct(ID);
    }
}
