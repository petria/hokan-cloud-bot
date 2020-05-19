package org.freakz.hokan.cloud.bot.eureka.frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HokanCloudBotFrontend {
  /*
  https://jasonwatmore.com/post/2020/04/28/angular-9-user-registration-and-login-example-tutorial
   */
  @Value("${spring.application.name}")
  private String appName;

  public static void main(String[] args) {
    SpringApplication.run(HokanCloudBotFrontend.class, args);
  }

}
