package com.trinhvu.location;

import com.trinhvu.commonlibrary.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {"com.trinhvu.location", "com.trinhvu.commonlibrary"})
@EnableConfigurationProperties(CorsConfig.class)
public class LocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationApplication.class, args);
    }

}
