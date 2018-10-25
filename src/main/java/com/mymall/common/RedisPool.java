package com.mymall.common;

import com.mymall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 对JedisPool的封装
 */
public class RedisPool {
    private static JedisPool pool;
    private static String host = PropertiesUtil.getProperty("redis1.host");
    private static int port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));

    //最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.maxTotal","20"));

    //最大空闲连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.maxIdle","10"));

    //最小空闲连接数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.minIdle","2"));

    //从连接池拿出时是否进行验证，true验证，取出的redis连接一定可用
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.testOnBorrow","true"));

    //放回连接池时是否进行验证，true验证，放回的redis连接一定可用
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.testOnReturn","true"));

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setBlockWhenExhausted(true);//资源耗尽时是否阻塞
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        pool = new JedisPool(config, host, port, 1000 * 2);
    }

    static {
        initPool();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }
}
