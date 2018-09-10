package com.yy.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by 稻草人 on 2018/9/10.
 * JSON格式化工具
 */
public class JsonUtil {

    public static String toJson(Object object){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setPrettyPrinting();

        Gson gson=gsonBuilder.create();
        return gson.toJson(object);

    }



}
