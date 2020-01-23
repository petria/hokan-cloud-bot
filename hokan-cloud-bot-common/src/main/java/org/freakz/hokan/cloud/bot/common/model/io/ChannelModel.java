package org.freakz.hokan.cloud.bot.common.model.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ChannelModel {

    private String name;
    private String topic;


}
