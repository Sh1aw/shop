package ru.innotech.discountapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DiscountApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscountApiApplication.class, args);
    }
}
