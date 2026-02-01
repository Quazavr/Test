package kafkaLog;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;


@SpringBootApplication
@Configuration
public class MainK {
    public static void main(String[] args) {
	SpringApplication.run(MainK.class, args);
	//Test.test();
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
	Map<String, Object> configProps = new HashMap<>();
	configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.202:9092");
	configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
	configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory());
	
	return factory;
    }
}
