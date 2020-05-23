package com.myapp.lostfound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(scanBasePackages = "com.myapp")
@EnableSwagger2
public class LostfoundApplication {
    public static void main(String[] args) {
            SpringApplication.run(LostfoundApplication.class, args);
        }

}
