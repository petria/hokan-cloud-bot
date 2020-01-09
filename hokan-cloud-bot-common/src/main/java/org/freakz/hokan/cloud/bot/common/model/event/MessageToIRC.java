package org.freakz.hokan.cloud.bot.common.model.event;

import lombok.Data;

//@Builder
@Data
//@RequiredArgsConstructor
public class MessageToIRC {

    public MessageToIRC() {
    }

    private long transactionId;

    private String target;

    private String channel;

    private String message;

}
