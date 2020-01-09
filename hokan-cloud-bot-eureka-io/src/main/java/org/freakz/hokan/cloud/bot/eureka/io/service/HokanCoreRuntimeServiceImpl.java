package org.freakz.hokan.cloud.bot.eureka.io.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.client.IrcEngineClient;
import org.freakz.hokan.cloud.bot.eureka.io.ircengine.HokanCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class HokanCoreRuntimeServiceImpl implements HokanCoreRuntimeService {

    @Autowired
    private IrcEngineClient ircEngineClient;

    private final Map<String, HokanCore> instanceMap = new ConcurrentHashMap<>();

    @Override
    public boolean putOnline(IrcServerConfigModel config) {
        String network = config.getNetwork();
        HokanCore core = instanceMap.get(network);
        if (core != null) {
            log.warn("Disconnecting existing instance: {}", core.toString());
            disconnectInstance(core);
        }

        core = new HokanCore(config, "HokanCld", this);
        try {
            core.connect(config.getServer(), config.getPort());
            core.joinChannel("#HokanCloud");
            instanceMap.put(network, core);
            return true;
        } catch (Exception e) {
            log.error("Connect error", e);
            return false;
        }

    }

    @Override
    public boolean putOffline(IrcServerConfigModel config) {
        String network = config.getNetwork();
        HokanCore core = instanceMap.remove(network);
        if (core != null) {
            disconnectInstance(core);
            return true;
        } else {
            log.warn("Not online core for network: {}", network);
            return false;
        }
    }

    private void disconnectInstance(HokanCore instance) {
        try {
            instance.disconnect();
            instance.dispose();
        } catch (Exception e) {
            log.error("disconnect", e);
        }
    }

    @Override
    public void coreDisconnected(HokanCore hokanCore) {
        disconnectInstance(hokanCore);
    }

    @Override
    public void publishRawIRCEvent(RawIRCEvent event) {
        log.debug("Publish event from: {} - TYPE: {}", event.getSource(), event.getType());
        try {
            ircEngineClient.rawIRCEvent(event);
        } catch (Exception e) {
            log.error("publish failed!", e);
        }
    }
}
