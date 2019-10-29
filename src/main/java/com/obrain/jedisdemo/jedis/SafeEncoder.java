package com.obrain.jedisdemo.jedis;

import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

import java.io.UnsupportedEncodingException;


/**
 *  编码
 *  @author zyy
 *  @date 2018年12月17日
 * */
public class SafeEncoder {

    public static byte[] encode(String str) {
        try {
            if (str == null) {
                throw new JedisDataException("value sent to redis cannot be null");
            } else {
                return str.getBytes("UTF-8");
            }
        } catch (UnsupportedEncodingException var2) {
            throw new JedisException(var2);
        }
    }
}
