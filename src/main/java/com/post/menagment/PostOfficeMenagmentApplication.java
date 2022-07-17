package com.post.menagment;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PostOfficeMenagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostOfficeMenagmentApplication.class, args);

		/*Long id  = */ /*ParcelRegistrationConsumer parcelRegistrationConsumer= new ParcelRegistrationConsumer();
		parcelRegistrationConsumer.consum();*/
//PostOfficeService.isPostOfficeAvailable(id)
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


}
