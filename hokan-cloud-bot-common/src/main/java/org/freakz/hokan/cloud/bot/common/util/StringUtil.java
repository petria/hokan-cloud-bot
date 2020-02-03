package org.freakz.hokan.cloud.bot.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

    public String normalizeKey(String key) {
        return key.toLowerCase();
    }

    public String normalizeChannel(String channel) {
        if (channel.startsWith("#")) {
            return channel;
        } else {
            return "#" + channel;
        }
    }
}
