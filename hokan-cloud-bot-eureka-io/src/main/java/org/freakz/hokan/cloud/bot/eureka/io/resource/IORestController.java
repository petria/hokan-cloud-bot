package org.freakz.hokan.cloud.bot.eureka.io.resource;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.api.io.IOResource;
import org.freakz.hokan.cloud.bot.common.model.ServiceResponse;
import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRC;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.service.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
public class IORestController implements IOResource {

    private final ConnectionManager connectionManager;

    @Autowired
    public IORestController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<IrcServerConfigModel> getConfiguredIRCServers() {
        return connectionManager.getConfiguredIRCServers();
    }

    @Override
    public ServiceResponse putIRCServerOnline(String network) {
        log.debug("Go online: {}", network);
        boolean ok = connectionManager.putOnline(network);
        if (ok) {
            return ServiceResponse.builder().status(OK.value()).response("OK").build();
        } else {
            return ServiceResponse.builder().status(INTERNAL_SERVER_ERROR.value()).response("ERROR").build();
        }
    }

    @Override
    public ServiceResponse putIRCServerOffline(String network) {
        log.debug("Go offline: {}", network);
        boolean ok = connectionManager.putOffline(network);
        if (ok) {
            return ServiceResponse.builder().status(OK.value()).response("OK").build();
        } else {
            return ServiceResponse.builder().status(INTERNAL_SERVER_ERROR.value()).response("ERROR").build();
        }
    }

    @Override
    public void postMessageToIRC(MessageToIRC messageToIRC) {
        boolean ok = connectionManager.sendMessageToIRC(messageToIRC);
        log.debug("post: {}", ok);
    }


}
