package com.icooking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableTransactionManagement
@Slf4j
public class IcookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(IcookingApplication.class, args);
        log.info("server start...");
    }

}
