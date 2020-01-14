package org.freakz.hokan.cloud.bot.common.model.event;

import lombok.Data;

//@Builder
@Data
//@RequiredArgsConstructor
public class ToIRCEvent {

    public ToIRCEvent() {
    }

    private long transactionId;

    private String target;

    private String channel;

    private String message;

}
