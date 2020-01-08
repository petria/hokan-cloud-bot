package org.freakz.hokan.cloud.bot.common.model.event;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//@Data
//@Builder
@Getter
public class RawIRCEvent {

    private final Map<String, String> parameters = new HashMap<>();

    private String source;
    private String type;

    public RawIRCEvent() {
    }

    public RawIRCEvent(String source, String type) {
        this.source = source;
        this.type = type;
    }

    private int parameterCount = 0;

    public RawIRCEvent parameter(String value) {
        parameters.put(parameterCount + "", value);
        parameterCount++;
        return this;
    }

}
