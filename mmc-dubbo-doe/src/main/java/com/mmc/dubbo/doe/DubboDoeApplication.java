package com.mmc.dubbo.doe;

import com.mmc.dubbo.doe.context.ApplicationReadyEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DubboDoeApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(DubboDoeApplication.class);
        springApplication.addListeners(new ApplicationReadyEventListener()); // load jars when startup
        springApplication.run(args);
    }
}

