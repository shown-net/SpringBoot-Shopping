package com.example.controller;

import com.example.pojo.Result;
import com.example.pojo.Product;
import com.example.service.ProductService;
import com.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
//@RequestMapping("/sign")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    // 增加新商品信息
    @PostMapping("/product/add")
    public Result add(@RequestBody Product product) {
        Boolean result = productService.getBy_Productname(product);
        if (result == Boolean.TRUE) {
            return Result.success("增加商品成功", null);
        } else {
            return Result.error("存在重复商品名，请重试", null);
        }
    }

    // 查询商品信息
    @PostMapping("/product/selectInfo")
    public Result info(@RequestBody String name) {
        Product product = productService.getInfoBy_name(name);
        return Result.success("查询商品信息成功", product);
    }

    // 根据种类查询商品信息
    @PostMapping("/product/selectInfoByKind")
    public Result infoKind(@RequestBody List<String> kindList) {
        if (!kindList.isEmpty()){
            for (String kind : kindList) {
                kind = kind.replace("\"", "");
            }
        }
        List<Product> product_List = productService.getInfoBy_kind(kindList);
        return Result.success("查询商品信息成功", product_List);
    }

    // 更新商品列表信息
    @PostMapping("/product/updateInfo")
    public Result updateInfo(@RequestBody Product product) {
        productService.UpdateInfoBy_ID(product);
        return Result.success("ok", null);
    }

    // 删除商品
    @PostMapping("/product/delete")
    public Result deleteInfo(@RequestBody Integer ID) {
        productService.deleteProductBy_ID(ID);
        return Result.success("ok", null);
    }


}
