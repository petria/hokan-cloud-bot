package org.freakz.hokan.cloud.bot.common.model.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ChannelUserModel {


    private String nick;
    private String prefix;

    private boolean isOp;
    private boolean hasVoice;

}
