package com.mymall.common;

import com.mymall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class RedissonManager {
    private Redisson redisson = null;
    private Config config = new Config();

    private static String host1 = PropertiesUtil.getProperty("redis1.host");
    private static int port1 = Integer.parseInt(PropertiesUtil.getProperty("resdis1.port"));
    private static String host2 = PropertiesUtil.getProperty("redis2.host");
    private static int port2 = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    /**
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次
     */
    @PostConstruct
    private void init(){
        try{
            config.useSingleServer().setAddress(new StringBuilder().append(host1).append(":").append("port1").toString());
            redisson = (Redisson) Redisson.create(config);
            log.info("Redisson 初始化完成");
        }catch (Exception e){
            log.error("init Redisson error", e);
        }
    }

    public Redisson getRedisson(){
        return redisson;
    }
}
