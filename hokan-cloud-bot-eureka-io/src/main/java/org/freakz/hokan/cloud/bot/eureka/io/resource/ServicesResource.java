package org.freakz.hokan.cloud.bot.eureka.io.resource;


import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.api.ServiceRequestController;
import org.freakz.hokan.cloud.bot.common.model.ServiceRequest;
import org.freakz.hokan.cloud.bot.common.model.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ServicesResource implements ServiceRequestController {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;


    @Override
    public ServiceResponse handleServiceRequest(ServiceRequest request) {
        return null;
    }
}
