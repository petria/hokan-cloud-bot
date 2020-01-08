package org.freakz.hokan.cloud.bot.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResponse {

    private int status;
    private String response;

}
