package org.freakz.hokan.cloud.bot.eureka.services.client;

import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.response.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient("HOKAN-CLOUD-BOT-EUREKA-IO")
public interface IrcIOClient {

    @RequestMapping(method = POST, value = "/engine/post-message")
    @ResponseBody
    ServiceResponse postMessageToIRC(@RequestBody MessageToIRCEvent messageToIRCEvent);

}
