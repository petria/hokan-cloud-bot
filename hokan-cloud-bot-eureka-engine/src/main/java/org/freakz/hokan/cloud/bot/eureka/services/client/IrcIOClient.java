package org.freakz.hokan.cloud.bot.eureka.services.client;

import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient("HOKAN-CLOUD-BOT-EUREKA-IO")
public interface IrcIOClient {

    @RequestMapping(method = RequestMethod.GET, value = "/servers")
    @ResponseBody
    List<IrcServerConfigModel> serviceRequest();

}
