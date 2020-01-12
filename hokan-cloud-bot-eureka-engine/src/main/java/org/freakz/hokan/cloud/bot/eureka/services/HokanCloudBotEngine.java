package org.freakz.hokan.cloud.bot.eureka.services;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.eureka.services.client.IrcIOClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({"org.freakz.hokan.cloud.bot"})
@EnableFeignClients
@EnableScheduling
@Slf4j
@SpringBootApplication
public class HokanCloudBotEngine {

    @Autowired
    private IrcIOClient client;

    @Value("${spring.application.name}")
    private String appName;

    public static void main(String[] args) {
        SpringApplication.run(HokanCloudBotEngine.class, args);
    }

/*    @Scheduled(fixedRate = 3000)
    public void timer() {
        log.debug("Timer!");

        List<IrcServerConfigModel> response = client.serviceRequest();
        log.debug("response:");
    }*/
}
