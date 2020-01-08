package org.freakz.hokan.cloud.bot.eureka.services.resource;


import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.api.RawIrcEventReceiver;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.eureka.services.service.CommandHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EngineResource implements RawIrcEventReceiver {

    private final CommandHandlerService commandHandlerService;

    @Autowired
    public EngineResource(CommandHandlerService commandHandlerService) {
        this.commandHandlerService = commandHandlerService;
    }

    @Override
    public void rawIRCEvent(RawIRCEvent event) {
        log.debug("Got event: {}", event.getSource());
        if (event.getType().equals("MESSAGE")) {
            commandHandlerService.handleMessage(event);
        }
    }

}
