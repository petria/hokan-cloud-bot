package org.freakz.hokan.cloud.bot.common.api.io;

import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.common.model.response.ServiceResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public interface IOConnectionManagerResource {

    @RequestMapping(method = GET, value = "/cm//servers")
    @ResponseBody
    List<IrcServerConfigModel> getConfiguredIRCServers();

    @RequestMapping(method = PUT, value = "/cm/servers/{network}")
    @ResponseBody
    ServiceResponse putIRCServerOnline(@PathVariable("network") String network);


    @RequestMapping(method = DELETE, value = "/cm/servers/{network}")
    @ResponseBody
    ServiceResponse putIRCServerOffline(@PathVariable("network") String network);


}

