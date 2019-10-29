package com.obrain.jedisdemo.jedis;

import com.obrain.jedisdemo.jedis.connection.Connection;

/**
 *  提供api服务
 *  @author zyy
 *  @date 2018年12月17日
 * */
public class Client {
    private Connection connection;

    public Client(String host, int port) {
        connection = new Connection(host, port);
    }

    public String set(String key, String value) {
        set(SafeEncoder.encode(key), SafeEncoder.encode(value));
        return connection.getStatusReply();
    }

    public void set(byte[] key, byte[] value) {
        this.connection.sendCommand(Protocol.Command.SET,new byte[][]{key,value});
    }

    public String get(String key) {
        this.connection.sendCommand(Protocol.Command.GET,SafeEncoder.encode(key));
        return connection.getStatusReply();
    }
}
