package org.freakz.hokan.cloud.bot.eureka.frontend.controller;

import lombok.Data;

@Data
public class JwtResponse {

  public JwtResponse(String token) {
    this.token = token;
  }

  private int id;
  private String username;
  private String firstName;
  private String lastName;

  private String token;

}
