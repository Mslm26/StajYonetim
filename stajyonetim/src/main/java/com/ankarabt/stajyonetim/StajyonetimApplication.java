package com.ankarabt.stajyonetim;

import com.ankarabt.stajyonetim.controller.PageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Scanner;

@SpringBootApplication
@EntityScan(basePackages = "com.ankarabt.stajyonetim.entity")
public class StajyonetimApplication {

	public static void main(String[] args) {


		SpringApplication.run(StajyonetimApplication.class, args);



	}

}

// http://localhost:8080/swagger-ui/index.html#/ SWAGGER URL