package com.eventviewer.eventviewerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.eventviewer"})
public class EventviewerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventviewerServiceApplication.class, args);
	}

}
