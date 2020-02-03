package org.freakz.hokan.cloud.bot.common.api.io;

import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.response.ServiceResponse;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public interface IOIrcEngineResource {

    @RequestMapping(method = GET, value = "/engine/{network}")
    @ResponseBody
    ServiceResponse getJoinedChannels(@PathVariable("network") String network);

    @RequestMapping(method = GET, value = "/engine/{network}/{channel}")
    @ResponseBody
    ServiceResponse getChannelJoinedUsers(@PathVariable("network") String network, @PathVariable("channel") String channel);

    @RequestMapping(method = POST, value = "/engine/post-message")
    @ResponseBody
    ServiceResponse postMessageToIRC(@RequestBody MessageToIRCEvent messageToIRCEvent);

    @RequestMapping(method = PUT, value = "/engine/{network}/{channel}/who")
    @ResponseBody
    void putWhoChannel(@PathVariable("network") String network, @PathVariable("channel") String channel);

    @RequestMapping(method = PUT, value = "/engine/{network}/{channel}/join")
    @ResponseBody
    void putJoinChannel(@PathVariable("network") String network, @PathVariable("channel") String channel);

    @RequestMapping(method = DELETE, value = "/engine/{network}/{channel}")
    @ResponseBody
    void deleteChannel(@PathVariable("network") String network, @PathVariable("channel") String channel);

}
