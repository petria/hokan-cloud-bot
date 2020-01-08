package org.freakz.hokan.cloud.bot.eureka.router.resource;


import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.api.RouterController;
import org.freakz.hokan.cloud.bot.common.model.RouterRequest;
import org.freakz.hokan.cloud.bot.common.model.RouterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RouterResource implements RouterController {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Override
    public ResponseEntity<RouterResponse> routeRequest(RouterRequest request) {
        log.debug("Route to: {}", request.getTarget());
        RouterResponse body = new RouterResponse();
        body.setTarget(request.getTarget());
        body.setPayload("routed to: " + request.getTarget());
        return ResponseEntity.ok(body);
    }

}
