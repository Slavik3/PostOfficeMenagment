package com.post.menagment.services;

import com.google.gson.Gson;
import com.post.menagment.dto.ParcelDTO;
import com.post.menagment.dto.ParcelRegistrationCompleted;
import com.post.menagment.model.PostOffice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ParcelRegistrationEvent {
    private final KafkaTemplate<String, ParcelRegistrationCompleted> producer;
    private PostOfficeService postOfficeService;

    @Autowired
    public ParcelRegistrationEvent(PostOfficeService postOfficeService, KafkaTemplate<String, ParcelRegistrationCompleted> producer) {
        this.postOfficeService = postOfficeService;
        this.producer = producer;
    }

    @Transactional
    @KafkaListener(topics = "parcelRegistrationInit1")
    public void consume(final ConsumerRecord<String, ParcelDTO> consumedParcel) {
        if(consumedParcel.key().equals("parcelRegistrationInitiate")) {
            Gson gson = new Gson();
            ParcelDTO parcelDTO = gson.fromJson(String.valueOf(consumedParcel.value()), ParcelDTO.class);
            Long idTo = parcelDTO.getIdTo();
            PostOffice postOffice = postOfficeService.getById(idTo);
            ParcelRegistrationCompleted prc = new ParcelRegistrationCompleted(parcelDTO, postOffice.getIsWorking());
            produce(prc);
        }
    }

    public void produce(ParcelRegistrationCompleted postOffice) {
            final String key = "parcelRegistrationCompleted";
            log.info("Producing record: {}\t{}", key, postOffice);
            producer.send("parcelRegistration1", key, postOffice).addCallback(
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
