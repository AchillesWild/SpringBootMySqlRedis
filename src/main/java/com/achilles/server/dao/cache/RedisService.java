package com.achilles.server.dao.cache;


import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisService {

    @Autowired
    RedissonClient redissonClient;

    public <T> T get(String key){

        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    public <T> void set(String key,T value,int duration){

        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value, Duration.ofSeconds(duration));
    }
}
