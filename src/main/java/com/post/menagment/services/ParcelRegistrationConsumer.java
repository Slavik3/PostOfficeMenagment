package com.post.menagment.services;

//import com.post.menagment.model.PostOffice;
//import com.post.menagment.model.PostOffice;
import com.post.menagment.model.Parcel;
import com.post.menagment.model.PostOffice;
import org.apache.kafka.clients.consumer.*;
import io.confluent.kafka.serializers.KafkaJsonDeserializerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;


public class ParcelRegistrationConsumer {

    @Autowired
    private PostOfficeService postOfficeService;//null

    public void consum() {
        Properties props = null;
        Long parcelIdTo = null;
        {
            try {
                props = loadConfig("src/main/resources/java.config");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // Add additional properties.
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaJsonDeserializer");
        props.put(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE, Parcel.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "demo-consumer-1");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final Consumer<String, Parcel> consumer = new KafkaConsumer<String, Parcel>(props);
        String topic="parcelRegistrationTest";
        consumer.subscribe(Arrays.asList(topic));

        Long total_count = 0L;


        try {
            while (true) {
                ConsumerRecords<String, Parcel> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, Parcel> record : records) {
                    String key = record.key();
                    Parcel parcel = record.value();
                    //total_count += value.getCount();
                    //parcelIdTo=value;
                    System.out.printf("Consumed record with key %s and value %s", key, parcel);

                    //boolean isPostOfficeAvailable = postOfficeService.isPostOfficeAvailable(parcel.getIdTo());

                    PostOffice postOffice = postOfficeService.getById(parcel.getIdTo());
                    System.out.println("postOffice==> " + postOffice);
                    //TODO get PostOffice
                    //if(isPostOfficeAvailable)
                        ParcelRegistrationCompletedProducer.send(postOffice);

                }
            }
        } finally {
            consumer.close();
            //return parcelIdTo;//?
        }


//перевіритит чи евейлебл
        //якщо евейлбл створити івент і відправити цей рекорд з статтусом ок
        //або зі сттатусом фейлд
    }




    public static Properties loadConfig(final String configFile) throws IOException {
        if (!Files.exists(Paths.get(configFile))) {
            throw new IOException(configFile + " not found.");
        }
        final Properties cfg = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
            cfg.load(inputStream);
        }
        return cfg;
    }

}
