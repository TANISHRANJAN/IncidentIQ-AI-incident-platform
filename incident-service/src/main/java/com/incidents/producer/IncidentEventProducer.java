package com.incidents.producer;



import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.incidents.constants.KafkaTopics;
import com.incidents.event.IncidentCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncidentEventProducer {

    private final KafkaTemplate<String, IncidentCreatedEvent>
            kafkaTemplate;

    public void publishIncidentCreatedEvent(
            IncidentCreatedEvent event
    ) {

        log.info(
                "Publishing incident event: {}",
                event
        );

        kafkaTemplate.send(
                KafkaTopics.INCIDENT_CREATED_TOPIC,
                event
        );
    }
}