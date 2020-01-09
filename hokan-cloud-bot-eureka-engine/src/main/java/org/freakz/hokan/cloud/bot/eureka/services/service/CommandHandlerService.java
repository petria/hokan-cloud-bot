package org.freakz.hokan.cloud.bot.eureka.services.service;

import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;

public interface CommandHandlerService {

    void handleMessage(RawIRCEvent event);

}
