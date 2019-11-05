package com.document.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.document.service.controllers.DocumentStorageController;

@SpringBootApplication
public class DocumentStorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentStorageServiceApplication.class, args);
	}

}
