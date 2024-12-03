package com.demo.xiying.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//全部都是静态方法，所以用final修饰
//其实就对人家的静态方法加了个异常捕获
public final class JsonUtils {

    private static final Logger LOGGER= LogManager.getLogger(JsonUtils.class);

    public static String parseObjToJson(Object object) {
        String string = null;
        try {
            string = JSONObject.toJSONString(object);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return string;
    }

    public static <T> T parseJsonToObj(String json, Class<T> c) {
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            return JSON.toJavaObject(jsonObject, c);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
