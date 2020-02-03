package org.freakz.hokan.cloud.bot.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceResponse {

    private int status;
    private String response;

    private Payload payload;

}
