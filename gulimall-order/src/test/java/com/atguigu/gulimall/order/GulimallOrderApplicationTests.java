package com.atguigu.gulimall.order;

import com.atguigu.gulimall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j
class GulimallOrderApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Test
    void sendMessageTests() {

        //1、发送消息,如果发送的消息是个对象，我们会使用序列化机制，将对象写出去。对象必须实现Serializable
        String msg = "Hello World";
        OrderReturnReasonEntity reasonEntity = new OrderReturnReasonEntity();
        reasonEntity.setId(1L);
        reasonEntity.setCreateTime(new Date());

        //2、发送的对象类型的消息，可以是一个json
        for(int i=0;i<10;i++){
            reasonEntity.setName("哈哈-"+i);
            rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",reasonEntity);
            log.info("消息发送完成{}",reasonEntity);
        }
    }

    /**
     * 1、如何创建Exchange[hello.java.exchange]、Queue、Binding
     *      1）、使用AmqpAdmin进行创建
     * 2、如何收发消息
     */
    @Test
    void createExchange() {
        //DirectExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
        //durable:是否持久化
        DirectExchange directExchange = new DirectExchange("hello-java-exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        log.info("exchange:[{}]创建成功","hello-java-exchange");
    }

    @Test
    void createQueue() {
        //exclusive:是否排他的，false:允许同时有多个连接到此queue
        Queue queue = new Queue("hello-java-queue",true,false,false);
        amqpAdmin.declareQueue(queue);
        log.info("queue:[{}]创建成功","hello-java-queue");
    }

    @Test
    void createBinding(){
        // String destination【目的地】,
        // DestinationType destinationType 【目的地类型】
        // String exchange【交换机】,
        // String routingKey【路由键】,
        // Map<String, Object> arguments【自定义参数】
        // 将exchange指定的交换机和destination目的地进行绑定，使用routingKey作为指定的路由键
        Binding binding = new Binding("hello-java-queue", Binding.DestinationType.QUEUE,
                "hello-java-exchange",
                "hello.java",null);
        amqpAdmin.declareBinding(binding);
        log.info("binding:[{}]创建成功","hello-java-binding");
    }



    @Test
    void contextLoads() {
    }

}
