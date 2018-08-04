package com.example.orderserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @Auther: tkn
 * @Date: 2018/8/4 10:30
 * @Description:  json转换工具
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     *  对象转换为json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *   json转对象
     * @param string
     * @param clasType
     * @return
     */
    public static   Object fromJson(String string,Class clasType){

        try {
            return objectMapper.readValue(string,clasType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     *   json转对象
     * @param string
     * @param typeReference
     * @return
     */
    public static   Object fromJson(String string, TypeReference typeReference){

        try {
            return objectMapper.readValue(string,typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
