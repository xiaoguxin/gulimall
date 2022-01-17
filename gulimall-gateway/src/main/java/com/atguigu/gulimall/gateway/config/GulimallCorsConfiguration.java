package com.atguigu.gulimall.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 配置跨域
 */
@Configuration
public class GulimallCorsConfiguration {

    @Bean // 添加过滤器
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        //1、配置跨域
        CorsConfiguration config = new CorsConfiguration();
        // 允许跨域的头
        config.addAllowedHeader("*");
        // 允许跨域的请求方式
        config.addAllowedMethod("*");
        // 允许跨域的请求来源
        config.addAllowedOrigin("*");
        // 是否允许携带cookie跨域
        config.setAllowCredentials(true);
        // 任意url都要进行跨域配置
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
