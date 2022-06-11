package com.atguigu.gulimall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.seckill.feign.CouponFeignService;
import com.atguigu.gulimall.seckill.feign.ProductFeignService;
import com.atguigu.gulimall.seckill.service.SeckillService;
import com.atguigu.gulimall.seckill.to.SeckillSkuRedisTo;
import com.atguigu.gulimall.seckill.vo.SeckillSessionsWithSkus;
import com.atguigu.gulimall.seckill.vo.SeckillSkuVo;
import com.atguigu.gulimall.seckill.vo.SkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    private final String SESSIONS_CACHE_PREFIX = "seckill:sessions:";
    private final String SKUKILL_CACHE_PREFIX = "seckill:skus";
    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";//+商品随机码

    @Override
    public void uploadSeckillSkuLatest3Days() {
        //1、扫描最近三天需要参与秒杀的活动
        R session = couponFeignService.getLates3DaySession();
        if (session.getCode() == 0){
            //上架商品
            List<SeckillSessionsWithSkus> sessionData = session.getData(new TypeReference<List<SeckillSessionsWithSkus>>() {
            });
            //缓存到redis
            if(sessionData!=null && sessionData.size()>0){
                //1、缓存活动信息
                saveSessionInfos(sessionData);

                //2、缓存活动的关联商品
                saveSessionSkuInfos(sessionData);
            }

        }
    }

    private void saveSessionInfos(List<SeckillSessionsWithSkus> sessions){
        sessions.stream().forEach(session ->{
            Long startTime = session.getStartTime().getTime();
            Long endTime = session.getEndTime().getTime();
            String key = SESSIONS_CACHE_PREFIX+startTime+"_"+endTime;
            Boolean hasKey = redisTemplate.hasKey(key);
            if (!hasKey){
                List<String> collect = session.getRelationSkus().stream().map(item->item.getPromotionSessionId()+"_"+item.getSkuId().toString()).collect(Collectors.toList());
                //缓存活动信息
                redisTemplate.opsForList().leftPushAll(key,collect);
            }
        });
    }


    private void saveSessionSkuInfos(List<SeckillSessionsWithSkus> sessions){

        sessions.stream().forEach(session -> {
            //准备hash操作
            BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
            session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                //4、随机码？ seckill?skuId&key=dafff;
                String token = UUID.randomUUID().toString().replace("-", "");

                if(!ops.hasKey(seckillSkuVo.getPromotionSessionId().toString()+"_"+seckillSkuVo.getSkuId().toString())){
                    //缓存商品
                    SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();

                    //1、sku的基本数据
                    R skuInfo = productFeignService.getSkuInfo(seckillSkuVo.getSkuId());
                    if (skuInfo.getCode() == 0){
                        SkuInfoVo info = skuInfo.getData2("skuInfo", new TypeReference<SkuInfoVo>() {
                        });
                        redisTo.setSkuInfoVo(info);

                    }


                    //2、sku的秒杀信息
                    BeanUtils.copyProperties(seckillSkuVo,redisTo);

                    //3、设置上当前商品的秒杀时间信息
                    redisTo.setStartTime(session.getStartTime().getTime());
                    redisTo.setEndTime(session.getEndTime().getTime());


                    redisTo.setRandomCode(token);


                    String jsonString = JSON.toJSONString(redisTo);
                    ops.put(seckillSkuVo.getPromotionSessionId()+"_"+seckillSkuVo.getSkuId().toString(),jsonString);

                    //如果当前这个场次的商品的库存信息已经上架就不需要上架

                    //5、使用库存作为分布式的信号量 限流；
                    RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                    //商品可以秒杀的数量作为信号量
                    semaphore.trySetPermits(seckillSkuVo.getSeckillCount().intValue());
                }

           });
        });
    }

}
