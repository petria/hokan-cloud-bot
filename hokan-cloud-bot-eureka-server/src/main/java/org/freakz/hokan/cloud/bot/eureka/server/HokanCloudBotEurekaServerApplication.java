package org.freakz.hokan.cloud.bot.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
//@EnableAdminServer
public class HokanCloudBotEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HokanCloudBotEurekaServerApplication.class, args);
    }
}
