package org.freakz.hokan.cloud.bot.eureka.services.resource;


import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.api.RawIrcEventReceiver;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EngineResource implements RawIrcEventReceiver {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Override
    public void rawIRCEvent(RawIRCEvent rawIRCEvent) {
        log.debug("Got event: {}", rawIRCEvent.getSource());
    }

}
