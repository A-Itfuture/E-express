package com.itfuture.e.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**redis工具类
 * @author： wxh
 * @version：v1.0
 * @date： 2022/11/30 09:29
 */
@Component
public class RedisUtil{

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static RedisUtil redisUtil;

    private static ValueOperations<String, Object> valueOperations;


    @PostConstruct
    public void init(){
        redisUtil = this;
        redisUtil.redisTemplate = this.redisTemplate;
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        valueOperations = redisTemplate.opsForValue();

    }

    /**
     * redis存入数据
     * @param key 键名
     * @param value 值
     * @param time 保存时间
     * @param timeUnit 时间单位
     */
    public static  void saveValue(String key, Object value, int time, TimeUnit timeUnit){
        //redisUtil.redisTemplate.opsForValue().set(key,value,time,timeUnit);
        valueOperations.set(key,value,time,timeUnit);
    }

    /**
     * 获取redis中的值
     * @param key 键名
     * @param <T>
     * @return
     */
    public static <T> T getValue(String key){
        //ValueOperations valueOperations = redisUtil.redisTemplate.opsForValue();
        return (T) valueOperations.get(key);
    }

    /**
     * 删除单个对象
     * @param key
     * @return
     */
    public static boolean deleteValue(String key){
        return Boolean.TRUE.equals(redisUtil.redisTemplate.delete(key));
    }


    /**
     * 获取key过期时间
     * @param key
     * @return
     */
    public static long getExpireTime(String key){
        return valueOperations.getOperations().getExpire(key);
    }
}
