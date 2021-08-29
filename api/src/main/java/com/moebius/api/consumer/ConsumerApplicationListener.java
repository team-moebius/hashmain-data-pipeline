package com.moebius.api.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.event.ConsumerStoppedEvent;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class ConsumerApplicationListener implements ApplicationListener<ConsumerStoppedEvent> {

    private final AdminClient adminClient;

    public ConsumerApplicationListener(AdminClient adminClient) {
        this.adminClient = adminClient;
    }

    @Override
    public void onApplicationEvent(ConsumerStoppedEvent event) {
        var container = event.getContainer(AbstractMessageListenerContainer.class);
        String containerGroupId = container.getGroupId();
        if (containerGroupId.startsWith("data-api")) {
            log.info("delete consumer group : {}", containerGroupId);

            var result = adminClient.deleteConsumerGroups(List.of(containerGroupId)).deletedGroups();
            result.values().forEach(o -> {
                try {
                    o.get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("can not delete consumer group", e);
                }
            });
        }
    }
}
