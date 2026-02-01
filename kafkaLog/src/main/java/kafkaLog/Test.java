package kafkaLog;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Test {
    static void test() {
	Properties props = new Properties();
	props.put("bootstrap.servers", "192.168.0.202:9092");
	props.put("group.id", "test-consumer-group");
	props.put("enable.auto.commit", "true");
	props.put("auto.commit.interval.ms", "1000");
	props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
	try {
	    consumer.subscribe(Arrays.asList("quickstart-events"));

	    while (true) {
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
		if(records.count() != 0) {
		    System.err.println("records size=>"+records.count());
		    for(ConsumerRecord<String, String> record : records)
			    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
		}
		
		Thread.sleep(100);
	    }

	} catch(Exception ex) {
	    ex.printStackTrace();
	} finally {
	    consumer.close();
	}
    }
}
