package org.freakz.hokan.cloud.bot.common.api;

import org.freakz.hokan.cloud.bot.common.model.ServiceRequest;
import org.freakz.hokan.cloud.bot.common.model.ServiceResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public interface ServiceRequestController {

    @RequestMapping(method = RequestMethod.POST, value = "/service")
    @ResponseBody
    ServiceResponse handleServiceRequest(@RequestBody ServiceRequest request);

}
