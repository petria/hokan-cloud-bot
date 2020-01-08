package org.freakz.hokan.cloud.bot.common.api;

import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public interface RawIrcEventReceiver {

    @RequestMapping(method = POST, value = "/raw_irc_event")
    @ResponseBody
    void rawIRCEvent(@RequestBody RawIRCEvent rawIRCEvent);

}
