package com.post.menagment.services;

import com.google.gson.Gson;
import com.post.menagment.dto.PostOfficeDTO;
import com.post.menagment.dto.Parcel;
import com.post.menagment.dto.SendParcelRegistrationCompleted;
import com.post.menagment.model.PostOffice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ParcelRegistrationConsumer {

    private final KafkaTemplate<String, SendParcelRegistrationCompleted> producer;

    private PostOfficeService postOfficeService;
    private ModelMapper modelMapper;

    @Autowired
    public ParcelRegistrationConsumer(PostOfficeService postOfficeService, ModelMapper modelMapper, KafkaTemplate<String, SendParcelRegistrationCompleted> producer) {
        this.postOfficeService = postOfficeService;
        this.modelMapper = modelMapper;
        this.producer = producer;
    }

    @Transactional
    @KafkaListener(topics = "parcelRegistrationTest")
    public void consume(final ConsumerRecord<String, Parcel> consumedParcel) {
        Gson gson = new Gson();
        Parcel parcel = gson.fromJson(String.valueOf(consumedParcel.value()), Parcel.class);
        Long idTo = parcel.getIdTo();
        PostOffice postOffice = postOfficeService.getById(idTo);
        SendParcelRegistrationCompleted sprc = new SendParcelRegistrationCompleted(parcel, postOffice.getIsWorking());
        produce(sprc);
    }

    public void produce(SendParcelRegistrationCompleted postOffice) {
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
    }


}
