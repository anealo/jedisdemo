package com.obrain.jedisdemo.jedis;

import java.io.IOException;
import java.io.OutputStream;

/**
 *  进行协议编码
 *  @author zyy
 *  @date 2018年12月17日
 * */
public class Protocol {
    /**
     * *    <参数数量> CR LF
     * $    <参数 1 的字节数量> CR LF
     *      <参数 1 的数据> CR LF
     *      ...
     * $    <参数 N 的字节数量> CR LF
     *      <参数 N 的数据> CR LF
     * */

    public static final String PARAM_BYTE_NUM  = "$";
    public static final String PARAM_NUM       = "*";
    public static final String TERMINATION     = "\r\n";


    public static void sendCommand(OutputStream outputStream, Command command, byte[]... b) {
        /*
            照着 SET mykey myvalue 的格式进行编码：
            *3
            $3
                SET
            $5
                mykey
            $7
                myvalue
            最终如下：
            "*3\r\n$3\r\nSET\r\n$5\r\nmykey\r\n$7\r\nmyvalue\r\n"
        */
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(PARAM_NUM).append(b.length + 1).append(TERMINATION);
        stringBuffer.append(PARAM_BYTE_NUM).append(command.name().length()).append(TERMINATION);
        stringBuffer.append(command).append(TERMINATION);
        for (byte[] arg : b) {
            stringBuffer.append(PARAM_BYTE_NUM).append(arg.length).append(TERMINATION);
            stringBuffer.append(new String(arg)).append(TERMINATION);
        }
        try {
            outputStream.write(stringBuffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static enum Command {
        SET,
        GET;
    }
}
