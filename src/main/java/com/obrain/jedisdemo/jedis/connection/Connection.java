package com.obrain.jedisdemo.jedis.connection;

import com.obrain.jedisdemo.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 建立连接
 *
 * @author zyy
 * @date 2018年12月17日
 */
public class Connection {
    private Socket socket;
    private String host;
    private int port;
    private OutputStream outputStream;
    private InputStream inputStream;

    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    //发送命令
    public Connection sendCommand(Protocol.Command cmd, byte[]... args) {
        try {
            this.connect();
            Protocol.sendCommand(this.outputStream, cmd, args);
            //++this.pipelinedCommands;
            return this;
        } catch (JedisConnectionException var6) {
            throw var6;
        }
    }

    //如果未建立连接，则scoket 连接
    public void connect() {
        try {
            if (!isConnected()) {
                socket = new Socket(host, port);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //判断是否已建立连接
    public boolean isConnected() {
        return socket != null && socket.isBound() && !socket.isClosed() && socket.isConnected()
                && !socket.isInputShutdown() && !socket.isOutputShutdown();
    }

    //获取返回信息
    public String getStatusReply() {
        byte b[] = new byte[1024];
        try {
            socket.getInputStream().read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(b);
    }
}
