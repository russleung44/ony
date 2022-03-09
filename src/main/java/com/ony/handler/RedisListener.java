package com.ony.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author TonyLeung
 * @date 2022/3/9
 */
@Slf4j
@Component
public class RedisListener extends KeyExpirationEventMessageListener {

    public RedisListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Redis过期key: {}", message);
    }

}
