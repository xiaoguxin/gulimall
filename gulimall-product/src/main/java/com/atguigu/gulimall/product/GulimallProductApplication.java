package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 *   原理
 *   CacheAutoConfiguration -> RedisCacheConfiguration ->
 *   自动配置了RedisCacheManager->初始化所有的缓存-> 每个缓存决定使用什么配置
 *   ->如果redisCacheConfiguration有就用已有的，没有就用默认配置
 *   ->想改缓存的配置，只需要给容器中放一个RedisCacheConfiguration即可
 *   ->就会应用到当前RedisCacheManager管理的所有缓存分区中
 */
@EnableRedisHttpSession //整合redis作为session存储
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.atguigu.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
