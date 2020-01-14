package org.freakz.hokan.cloud.bot.eureka.io.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.ircengine.HokanCore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Service
@Slf4j
public class HokanCoreRuntimeServiceImpl implements HokanCoreRuntimeService {

    private final ConnectionManager connectionManager;

    private final Map<String, HokanCore> instanceMap = new ConcurrentHashMap<>();

    //    @Autowired
    public HokanCoreRuntimeServiceImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

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
            instanceMap.put(network, core);
            core.connect(config.getServer(), config.getPort());
            return true;
        } catch (Exception e) {
            log.error("Connect error", e);
            instanceMap.remove(network);
            return false;
        }

    }

    @Override
    public boolean networkJoinChannel(String network, String channel) {
        HokanCore core = instanceMap.get(network);
        if (core != null) {
            core.joinChannel(channel);
            return true;
        }
        return false;
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

    @Override
    public HokanCore findTargetCore(String target) {
        for (HokanCore core : instanceMap.values()) {
            if (core.getUniqueIdent().equals(target)) {
                return core;
            }
        }
        return null;
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
    public void publishRawIRCEvent(RawIRCEvent event) {
        connectionManager.publishRawIRCEvent(event);
    }

    // EVENT HANDLERS
    @Override
    public void coreDisconnected(HokanCore hokanCore) {
        disconnectInstance(hokanCore);
    }

    @Override
    public void coreConnected(HokanCore hokanCore) {
        connectionManager.coreConnected(hokanCore);
    }
}
