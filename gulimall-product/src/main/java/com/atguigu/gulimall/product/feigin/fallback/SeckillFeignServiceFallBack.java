package com.atguigu.gulimall.product.feigin.fallback;

import com.atguigu.common.exception.BizCode;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.product.feigin.SeckillFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SeckillFeignServiceFallBack implements SeckillFeignService {

    @Override
    public R getSkuSeckillInfo(Long skuId) {
        log.info("熔断方法调用...getSkuSeckillInfo");
        return R.error(BizCode.TO_MANY_REQUEST.getCode(),BizCode.TO_MANY_REQUEST.getMsg());
    }

}
