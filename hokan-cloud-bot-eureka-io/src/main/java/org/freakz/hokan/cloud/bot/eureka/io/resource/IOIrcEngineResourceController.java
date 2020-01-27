package org.freakz.hokan.cloud.bot.eureka.io.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.api.io.IOIrcEngineResource;
import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.ChannelModel;
import org.freakz.hokan.cloud.bot.common.model.response.JoinedChannelsServicePayload;
import org.freakz.hokan.cloud.bot.common.model.response.ServiceResponse;
import org.freakz.hokan.cloud.bot.eureka.io.service.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@Slf4j
public class IOIrcEngineResourceController implements IOIrcEngineResource {

    private final ConnectionManager connectionManager;

    @Override
    public ServiceResponse getJoinedChannels(String network) {
        List<ChannelModel> joinedChannels = connectionManager.getJoinedChannels(network);
        ServiceResponse response = ServiceResponse.builder().build();

        if (joinedChannels != null) {
            response.setPayload(JoinedChannelsServicePayload.builder().joinedChannels(joinedChannels).build());
            response.setResponse("OK");
            response.setStatus(OK.value());
        } else {
            response.setResponse("ERROR");
            response.setStatus(INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public ServiceResponse getChannelJoinedUsers(String network) {
        return null;
    }

    @Override
    @HystrixCommand
    public void postMessageToIRC(MessageToIRCEvent messageToIRCEvent) {
        boolean ok = connectionManager.sendMessageToIRC(messageToIRCEvent);
    }

    @Override
    public void putWhoIsChannel(String network, String channel) {

    }

    @Override
    public void putJoinIsChannel(String network, String channel) {

    }

    @Override
    public void putPartIsChannel(String network, String channel) {

    }

}