package org.freakz.hokan.cloud.bot.eureka.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({"org.freakz.hokan.cloud.bot"})
@EnableAsync
@EnableHystrixDashboard
@EnableHystrix
@EnableCircuitBreaker
@EnableFeignClients
@EnableJpaRepositories("org.freakz.hokan.cloud.bot.common.jpa")
@EntityScan(basePackages = {"org.freakz.hokan.cloud.bot.common.jpa"})
@EnableScheduling
@Slf4j
@SpringBootApplication
public class HokanCloudBotEurekaIO {

    @Value("${spring.application.name}")
    private String appName;

    public static void main(String[] args) {
        SpringApplication.run(HokanCloudBotEurekaIO.class, args);
    }

}
