package org.freakz.hokan.cloud.bot.eureka.io.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.api.io.IOIrcEngineResource;
import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.ChannelModel;
import org.freakz.hokan.cloud.bot.common.model.io.ChannelUserModel;
import org.freakz.hokan.cloud.bot.common.model.response.ChannelUsersPayload;
import org.freakz.hokan.cloud.bot.common.model.response.JoinedChannelsPayload;
import org.freakz.hokan.cloud.bot.common.model.response.ServiceResponse;
import org.freakz.hokan.cloud.bot.eureka.io.service.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.freakz.hokan.cloud.bot.common.util.StringUtil.normalizeChannel;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@Slf4j
public class IOIrcEngineResourceController implements IOIrcEngineResource {

    private final ConnectionManager connectionManager;

    @Override
    public ServiceResponse getJoinedChannels(String network) {
        List<ChannelModel> payload = connectionManager.getJoinedChannels(network);
        ServiceResponse response = ServiceResponse.builder().build();

        if (payload != null) {
            response.setPayload(JoinedChannelsPayload.builder().joinedChannels(payload).build());
            response.setResponse("OK");
            response.setStatus(OK.value());
        } else {
            response.setResponse("ERROR");
            response.setStatus(INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    public ServiceResponse getChannelJoinedUsers(String network, String channel) {
        List<ChannelUserModel> payload
                = connectionManager.getChannelUsers(network, normalizeChannel(channel));
        ServiceResponse response = ServiceResponse.builder().build();

        if (payload != null) {
            response.setPayload(ChannelUsersPayload.builder().joinedChannels(payload).build());
            response.setResponse("OK");
            response.setStatus(OK.value());
        } else {
            response.setResponse("ERROR");
            response.setStatus(INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @Override
    @HystrixCommand
    public ServiceResponse postMessageToIRC(MessageToIRCEvent messageToIRCEvent) {
        boolean ok = connectionManager.sendMessageToIRC(messageToIRCEvent);
        if (ok) {
            return ServiceResponse.builder()
                    .status(OK.value())
                    .response("OK")
                    .build();
        } else {
            return ServiceResponse.builder()
                    .status(INTERNAL_SERVER_ERROR.value())
                    .response("ERROR")
                    .build();

        }
    }

    @Override
    public ServiceResponse putWhoChannel(String network, String channel) {
        boolean ok = connectionManager.sendWhoChannel(network, normalizeChannel(channel));
        if (ok) {
            return ServiceResponse.builder()
                    .status(OK.value())
                    .response("OK")
                    .build();
        } else {
            return ServiceResponse.builder()
                    .status(INTERNAL_SERVER_ERROR.value())
                    .response("ERROR")
                    .build();

        }
    }

    @Override
    public ServiceResponse putJoinChannel(String network, String channel) {
        boolean ok = connectionManager.joinChannel(network, normalizeChannel(channel));
        if (ok) {
            return ServiceResponse.builder()
                    .status(OK.value())
                    .response("OK")
                    .build();
        } else {
            return ServiceResponse.builder()
                    .status(INTERNAL_SERVER_ERROR.value())
                    .response("ERROR")
                    .build();

        }
    }

    @Override
    public ServiceResponse deletePartChannel(String network, String channel) {
        boolean ok = connectionManager.partChannel(network, normalizeChannel(channel));
        if (ok) {
            return ServiceResponse.builder()
                    .status(OK.value())
                    .response("OK")
                    .build();
        } else {
            return ServiceResponse.builder()
                    .status(INTERNAL_SERVER_ERROR.value())
                    .response("ERROR")
                    .build();

        }

    }
}
