package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    public Integer code;
    public String msg;
    public Object data;
    //增删改响应
    public static Result success(String msg){
        return new Result(1,msg,null);
    }
    //查询成功响应
    public static Result success(String msg,Object data){
        return new Result(1,msg,data);
    }
    //查询失败响应
    public static Result error(String msg,Object data){
        return new Result(0,msg,data);
    }

}
