package com.obrain.jedisdemo.jedis;

public class Jedis {

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1",6379);
        System.out.println(client.set("name","myobrain"));
        System.out.println(client.get("name"));
    }
}
