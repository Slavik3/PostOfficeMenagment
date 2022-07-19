package com.post.menagment.services;

import com.google.gson.Gson;
import com.post.menagment.dto.PostOfficeDTO;
import com.post.menagment.dto.Parcel;
import com.post.menagment.model.PostOffice;
import com.post.menagment.repository.PostOfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static java.util.stream.IntStream.range;

@Log4j2
@Service
@RequiredArgsConstructor
public class ParcelRegistrationConsumer {

    //private final KafkaTemplate<String, PostOffice> producer;

    private PostOfficeService postOfficeService;
    private ModelMapper modelMapper;

    @Autowired
    public ParcelRegistrationConsumer(PostOfficeService postOfficeService, ModelMapper modelMapper) {
        this.postOfficeService = postOfficeService;
        this.modelMapper = modelMapper;
    }



    @Transactional
    @KafkaListener(topics = "parcelRegistrationTest")
    public void consume(final ConsumerRecord<String, Parcel> parcel) {
        Gson gson = new Gson();
        Parcel p = gson.fromJson(String.valueOf(parcel.value()), Parcel.class);

        log.info(".key()==> " + parcel.key());
        log.info(".value()==> " + parcel.value());
        Long idTo = p.getIdTo();
        log.info("idTo==> " + idTo);
        PostOffice postOffice = postOfficeService.getById(idTo);
        log.info("postOffice"+postOffice);
        PostOfficeDTO postOfficeDTO= modelMapper.map(postOffice, PostOfficeDTO.class);
        produce(postOfficeDTO);

        System.out.println("----");
    }


    public static void produce(PostOfficeDTO postOffice) {
        System.out.println("PostOfficeRegistrationProducer.send");
        Properties props = null;
        {
            try {
                props = loadConfig("src/main/resources/java.config");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String topic="postOffice";
        // Add additional properties.
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaJsonSerializer");

        Producer<String, PostOfficeDTO> producer = new KafkaProducer<String, PostOfficeDTO>(props);

        String key = "postOfficeKey";

        System.out.printf("Producing record: %s\t%s%n", key, postOffice);
        producer.send(new ProducerRecord<String, PostOfficeDTO>(topic, key, postOffice), new Callback() {
            @Override
            public void onCompletion(RecordMetadata m, Exception e) {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    System.out.printf("Produced record to topic %s partition [%d] @ offset %d%n", m.topic(), m.partition(), m.offset());
                }
            }
        });

        producer.flush();

        producer.close();
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



    /*public void produce(PostOfficeDTO postOffice) {
        System.out.println("ProducerExample");
            final String key = "postOfficeKey";
            log.info("Producing record: {}\t{}", key, postOffice);
            producer.send("postOffice", key, postOffice).addCallback(
                    result -> {
                        final RecordMetadata m;
                        if (result != null) {
                            m = result.getRecordMetadata();
                            log.info("Produced record to topic {} partition {} @ offset {}",
                                    m.topic(),
                                    m.partition(),
                                    m.offset());
                        }
                    },
                    exception -> log.error("Failed to produce to kafka", exception));
        producer.flush();
        log.info("10 messages were produced to topic {}", "ttt");
    }*/


}
