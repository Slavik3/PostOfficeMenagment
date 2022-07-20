package com.post.menagment.services;

import com.google.gson.Gson;
import com.post.menagment.dto.ParcelDTO;
import com.post.menagment.dto.ParcelRegistrationCompleted;
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
public class ParcelRegistrationEvent {

    private final KafkaTemplate<String, ParcelRegistrationCompleted> producer;

    private PostOfficeService postOfficeService;
    private ModelMapper modelMapper;

    @Autowired
    public ParcelRegistrationEvent(PostOfficeService postOfficeService, ModelMapper modelMapper, KafkaTemplate<String, ParcelRegistrationCompleted> producer) {
        this.postOfficeService = postOfficeService;
        this.modelMapper = modelMapper;
        this.producer = producer;
    }

    @Transactional
    @KafkaListener(topics = "parcelRegistrationInit")
    public void consume(final ConsumerRecord<String, ParcelDTO> consumedParcel) {
        if(consumedParcel.key().equals("parcelRegistrationInitiate")) {
            System.out.println();
            Gson gson = new Gson();
            ParcelDTO parcelDTO = gson.fromJson(String.valueOf(consumedParcel.value()), ParcelDTO.class);
            Long idTo = parcelDTO.getIdTo();
            PostOffice postOffice = postOfficeService.getById(idTo);
            ParcelRegistrationCompleted sprc = new ParcelRegistrationCompleted(parcelDTO, postOffice.getIsWorking());
            produce(sprc);
        }
    }

    public void produce(ParcelRegistrationCompleted postOffice) {
            final String key = "parcelRegistrationCompleted";
            log.info("Producing record: {}\t{}", key, postOffice);
            producer.send("parcelRegistration", key, postOffice).addCallback(
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
