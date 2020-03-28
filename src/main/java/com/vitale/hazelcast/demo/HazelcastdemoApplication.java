package com.vitale.hazelcast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.vitale")
@SpringBootApplication
public class HazelcastdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HazelcastdemoApplication.class, args);
	}

}
