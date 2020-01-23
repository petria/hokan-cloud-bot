package org.freakz.hokan.cloud.bot.common.api.io;

import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.response.ServiceResponse;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public interface IOIrcEngineResource {

    @RequestMapping(method = GET, value = "/joined-channels/{network}")
    @ResponseBody
    ServiceResponse getJoinedChannels(@PathVariable("network") String network);

    @RequestMapping(method = GET, value = "/joined-channels/{network}/{channel}")
    @ResponseBody
    ServiceResponse getChannelJoinedUsers(@PathVariable("network") String network);

    @RequestMapping(method = POST, value = "/post-message")
    @ResponseBody
    void postMessageToIRC(@RequestBody MessageToIRCEvent messageToIRCEvent);

    @RequestMapping(method = PUT, value = "/who-is-channel/{network}/{channel}")
    @ResponseBody
    void putWhoIsChannel(@PathVariable("network") String network, @PathVariable("channel") String channel);

    @RequestMapping(method = PUT, value = "/join-channel/{network}/{channel}")
    @ResponseBody
    void putJoinIsChannel(@PathVariable("network") String network, @PathVariable("channel") String channel);

    @RequestMapping(method = PUT, value = "/part-channel/{network}/{channel}")
    @ResponseBody
    void putPartIsChannel(@PathVariable("network") String network, @PathVariable("channel") String channel);

}
