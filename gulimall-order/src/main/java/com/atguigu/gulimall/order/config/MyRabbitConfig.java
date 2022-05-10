package com.atguigu.gulimall.order.config;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;

@Configuration
public class MyRabbitConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 使用JSON序列化机制，进行消息转换
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /*
     *  定制RabbitTemplate
     *  1、服务收到消息就回调
     *      1、spring.rabbitmq.publisher-confirm-type=correlated
     *      2、设置确认回调ConfirmCallback
     *  2、消息正确抵达队列进行回调
     *      1、spring.rabbitmq.publisher-returns=true
     *          spring.rabbitmq.template.mandatory=true
     *      2、设置确认回调ReturnCallback
     *
     *  3、消费端确让（保证每个消息被正确消费，此时才可以broker删除这个消息）
     */
    @PostConstruct //MyRabbitConfig对象创建完成以后,执行这个方法
    public void initRabbitTemplate() {
        //设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /***
             * 1、只要消息抵达Broker就ack=true
             * @param correlationData 当前消息的唯一关联数据(消息的唯一id)，
             * @param ack 消息是否成功收到
             * @param cause 失败原因
             */
            @Override
            public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause) {
                System.out.println("confirm...CorrelationData[" + correlationData + "]==>ack[" + ack + "]==>cause[" + cause + "]");
            }
        });


        //设置消息抵达队列的确认回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 只要消息没有投递到指定队列，就触发这个失败回调
             * @param message   投递失败的消息详细信息
             * @param replyCode 回复的状态码
             * @param replyText 回复的文本内容
             * @param exchange  当时这个消息发给哪个交换机
             * @param routingKey 当时这个消息用哪个路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("Fail Message[" + message + "]==>replyCode[" + replyCode
                        + "]==>replyText[" + replyText + "]==>[exchange" + exchange + "]==>routingKey[" + routingKey + "]");
            }
        });
    }
}
