package org.freakz.hokan.cloud.bot.common.api.io;

import org.freakz.hokan.cloud.bot.common.model.ServiceResponse;
import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRC;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public interface IOResource {

    @RequestMapping(method = GET, value = "/servers")
    @ResponseBody
    List<IrcServerConfigModel> getConfiguredIRCServers();


    @RequestMapping(method = PUT, value = "/servers/{network}")
    @ResponseBody
    ServiceResponse putIRCServerOnline(@PathVariable("network") String network);


    @RequestMapping(method = DELETE, value = "/servers/{network}")
    @ResponseBody
    ServiceResponse putIRCServerOffline(@PathVariable("network") String network);

    @RequestMapping(method = POST, value = "/post-message")
    @ResponseBody
    void postMessageToIRC(@RequestBody MessageToIRC messageToIRC);


}

