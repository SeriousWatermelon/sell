package com.yy.utils;

import com.yy.constant.CookieConstant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 稻草人 on 2018/10/27.
 * cookie存取工具类
 */
public class CookieUtil {

    /**
     *  设置cookie
     * @param response
     * @param name  cookie的key值
     * @param value cookie的value值
     * @param maxAge    cookie的过期时间，单位：s
     */
    public static void set(HttpServletResponse response,
                           String name,String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge); //cookie过期时间s
        response.addCookie(cookie);
    }

    /**
     * 返回对应key的cookie内容
     * @param request
     * @param key
     * @return
     */
    public static Cookie get(HttpServletRequest request,
                           String key){
        Map<String,Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(key)){
            return cookieMap.get(key);
        }else{
            return null;
        }
    }

    /**
     * 将数组格式的cookie内容转换成map类型
     * 封装成map
     * @param request
     * @return
     */
    private static Map<String,Cookie> readCookieMap(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Map<String,Cookie> cookieMap = new HashMap<>();
        if(cookies != null){
            for(Cookie cookie: cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }

}
