package kafkaLog.service;

import org.apache.kafka.common.utils.Bytes;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Test {
    
    
    @KafkaListener(topics = "quickstart-events", groupId = "test-consumer-group")
    public void listen(Pesik message) {
	 System.out.println("Received message: " + message);
    }
    
   

}
