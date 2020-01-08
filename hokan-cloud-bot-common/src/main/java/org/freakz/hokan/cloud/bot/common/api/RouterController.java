package org.freakz.hokan.cloud.bot.common.api;

import org.freakz.hokan.cloud.bot.common.model.RouterRequest;
import org.freakz.hokan.cloud.bot.common.model.RouterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface RouterController {

    @PostMapping("/route")
    @ResponseBody
    ResponseEntity<RouterResponse>  routeRequest(@RequestBody RouterRequest request);

}
