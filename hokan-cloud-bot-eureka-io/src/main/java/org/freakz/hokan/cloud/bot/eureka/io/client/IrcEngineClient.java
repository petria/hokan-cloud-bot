package org.freakz.hokan.cloud.bot.eureka.io.client;

import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient("HOKAN-CLOUD-BOT-EUREKA-ENGINE")
public interface IrcEngineClient {


    @RequestMapping(method = POST, value = "/raw_irc_event")
    @ResponseBody
    void rawIRCEvent(@RequestBody RawIRCEvent rawIRCEvent);

}
