package org.freakz.hokan.cloud.bot.eureka.io.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.api.io.IOResource;
import org.freakz.hokan.cloud.bot.common.model.ServiceResponse;
import org.freakz.hokan.cloud.bot.common.model.event.ToIRCEvent;
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
    @Hystrix
    public List<IrcServerConfigModel> getConfiguredIRCServers() {
        return connectionManager.getConfiguredIRCServers();
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallBackServiceRespond")
    public ServiceResponse putIRCServerOnline(String network) {
        log.debug("Go online: {}", network);
        boolean ok = connectionManager.putOnline(network);
        if (ok) {
            return ServiceResponse.builder().status(OK.value()).response("OK").build();
        } else {
            return ServiceResponse.builder().status(INTERNAL_SERVER_ERROR.value()).response("ERROR").build();
        }
    }

    public ServiceResponse fallBackServiceRespond(String param) {
        return ServiceResponse.builder().status(INTERNAL_SERVER_ERROR.value()).response("FALLBACK: ERROR").build();
    }

    @Override
    @HystrixCommand
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
    @HystrixCommand
    public void postMessageToIRC(ToIRCEvent toIRCEvent) {
        boolean ok = connectionManager.sendMessageToIRC(toIRCEvent);
//        log.debug("post: {}", ok);
    }


}
