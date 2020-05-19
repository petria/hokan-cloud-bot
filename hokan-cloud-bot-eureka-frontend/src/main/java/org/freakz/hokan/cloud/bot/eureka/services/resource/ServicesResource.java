package org.freakz.hokan.cloud.bot.eureka.services.resource;


import com.netflix.discovery.EurekaClient;
import org.freakz.hokan.cloud.bot.common.model.ServiceRequest;
import org.freakz.hokan.cloud.bot.common.model.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

//@RestController
//@Slf4j
public class ServicesResource { // implements ServiceRequestController {

  @Autowired
  @Lazy
  private EurekaClient eurekaClient;

  //    @Override
  public ServiceResponse handleServiceRequest(ServiceRequest request) {
    return ServiceResponse.builder().response("response from services").build();
  }
}
