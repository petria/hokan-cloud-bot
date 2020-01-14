package org.freakz.hokan.cloud.bot.common.model.event;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

//@RequiredArgsConstructor
//@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RawIRCEvent {

    private final Map<String, String> parameters = new HashMap<>();

    private long transactionId;
    private String source;
    private String type;

    public RawIRCEvent() {
    }

    public RawIRCEvent addParameter(String value) {
        int size = parameters.size();
        parameters.put(size + "", value);
        return this;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
