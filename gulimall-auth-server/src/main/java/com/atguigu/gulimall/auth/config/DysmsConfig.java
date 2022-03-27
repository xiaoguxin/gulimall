package com.atguigu.gulimall.auth.config;

import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: MR.rp
 * @Date: 2021/8/3 14:31
 * @Description:
 */

@Configuration
public class DysmsConfig {

    @Value(value = "${dysms.accessKeyId}")
    private  String   accessKeyId;

    @Value(value = "${dysms.accessKeySecret}")
    private  String  accessKeySecret;

    @Bean
    public com.aliyun.dysmsapi20170525.Client careateClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
}
