package org.freakz.hokan.cloud.bot.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

    public String normalizeKey(String key) {
        return key.toLowerCase();
    }

}
