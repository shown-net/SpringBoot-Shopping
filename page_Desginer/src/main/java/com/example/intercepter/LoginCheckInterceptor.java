package com.example.intercepter;

import com.alibaba.fastjson.JSONObject;
import com.example.pojo.Result;
import com.example.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override //目标资源运行前执行，返回true放行，返回false不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //处理预检请求
        if(request.getMethod().equals("OPTIONS"))
        {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        //获取请求中的令牌
        String jwt = request.getHeader("token");
        //判断令牌是否存在,如果不存在则返回错误信息
        if (!StringUtils.hasLength(jwt)) {
            Result error = Result.error("Not_Login", null);
            //手动转换json对象 ------>阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }
        //尝试解析jwt令牌
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) { //jwt解析失败，返回错误信息
            Result error = Result.error("Not_Login", null);
            //手动转换json对象 ------>阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }
        //放行该请求
        System.out.println("放行该次请求");
        return true;
    }

    @Override   //目标资源运行后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override   //视图渲染完毕后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterHandle");
    }
}
