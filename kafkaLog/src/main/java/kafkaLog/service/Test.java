package kafkaLog.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Test {
    
    @KafkaListener(topics = "quickstart-events", groupId = "test-consumer-group")
    public void listen(String message) {
	 System.out.println("Received message: " + message);
    }

}
