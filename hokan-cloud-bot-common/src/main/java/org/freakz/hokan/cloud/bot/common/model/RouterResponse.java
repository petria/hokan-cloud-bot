package org.freakz.hokan.cloud.bot.common.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RouterResponse {

    private String target;

    private String payload;

}
