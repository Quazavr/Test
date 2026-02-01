package kafkaLog;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.BytesJacksonJsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJacksonJavaTypeMapper;
import org.springframework.kafka.support.mapping.JacksonJavaTypeMapper.TypePrecedence;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import kafkaLog.service.Pesik;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.Bytes;

@SpringBootApplication
@Configuration
@EnableKafka
public class MainK {
    public static void main(String[] args) {
	SpringApplication.run(MainK.class, args);
	// Test.test();
    }

    @Bean
    public ConsumerFactory<String, Pesik> consumerFactory() {
	Map<String, Object> configProps = new HashMap<>();
	configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.202:9092");
	configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
	configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
	JacksonJsonDeserializer<Pesik> pesikDeser = new JacksonJsonDeserializer<Pesik>();
	// pesikDeser.addTrustedPackages("kafkaProducer.model");

	Map<String, Object> props = new HashMap<>();
	props.put(JacksonJsonDeserializer.TYPE_MAPPINGS, "pesik:kafkaLog.service.Pesik");
	pesikDeser.configure(props, true);
	return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), pesikDeser);
    }

    @Bean
    public RecordMessageConverter converter() {
	BytesJacksonJsonMessageConverter converter = new BytesJacksonJsonMessageConverter();
	DefaultJacksonJavaTypeMapper typeMapper = new DefaultJacksonJavaTypeMapper();
	typeMapper.setTypePrecedence(TypePrecedence.TYPE_ID);
	typeMapper.addTrustedPackages("kafkaLog.service");
	Map<String, Class<?>> mappings = new HashMap<>();
	mappings.put("pesik", Pesik.class);
	typeMapper.setIdClassMapping(mappings);
	converter.setTypeMapper(typeMapper);
	return converter;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Pesik> kafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, Pesik> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory());

	return factory;
    }
}
