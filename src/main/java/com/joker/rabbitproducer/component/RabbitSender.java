package com.joker.rabbitproducer.component;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @version 1.0.0
 * @ClassName RabbitSender.java
 * @Package com.joker.rabbitproducer.component
 * @Author Joker
 * @Description 消息发送体
 * @CreateTime 2021年08月27日 14:29:00
 */
@Component
public class RabbitSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 确认消息的回调监听接口，用于确认消息是否被broker收到
     */
    final RabbitTemplate.ConfirmCallback  confirmCallback = new RabbitTemplate.ConfirmCallback() {

        /**
         *
         * @param correlationData 作为一个唯一标识
         * @param b ack broker 是否落盘成功
         * @param s 失败的一些异常信息
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean b, String s) {

        }
    };

    /**
     * 发送消息的方法
     * @param message 具体消息内容
     * @param properties 额外附加属性
     * @throws Exception
     */
    public void send(Object message, Map<String,Object> properties) throws Exception{
        MessageHeaders headers = new MessageHeaders(properties);
        Message<?> msg = MessageBuilder.createMessage(message, headers);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        /*指定业务唯一id*/
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString().trim());
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                System.out.println("-------> post to do:" + message);
                return message;
            }
        };


        rabbitTemplate.convertAndSend("exchange-1","springboot.rabbit",msg,messagePostProcessor,cd);

    }
}
