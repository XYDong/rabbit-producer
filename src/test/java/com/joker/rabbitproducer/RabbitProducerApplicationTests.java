package com.joker.rabbitproducer;

import com.joker.rabbitproducer.component.RabbitSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RabbitProducerApplicationTests {

    @Autowired
    private RabbitSender rabbitSender;

    @Test
    void contextLoads() {
    }

    @Test
    public void sendTest() throws Exception {
        Map<String,Object> properties = new HashMap<>(6);
        properties.put("attr1","123456");
        properties.put("attr2","abcde");
        rabbitSender.send("hello rabbitmq!!!",properties);

        Thread.sleep(10000);
    }

}
