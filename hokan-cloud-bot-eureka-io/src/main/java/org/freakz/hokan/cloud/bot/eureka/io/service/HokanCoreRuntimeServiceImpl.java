package org.freakz.hokan.cloud.bot.eureka.io.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.ircengine.HokanCoreNew;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Service
@Slf4j
public class HokanCoreRuntimeServiceImpl implements HokanCoreRuntimeService {

    private final ConnectionManager connectionManager;

//    private final Map<String, HokanCore> instanceMap = new ConcurrentHashMap<>();

    private final Map<String, HokanCoreNew> instanceMapNew = new ConcurrentHashMap<>();

    //    @Autowired
    public HokanCoreRuntimeServiceImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean putOnline(IrcServerConfigModel config) {
        String network = config.getNetwork();
        HokanCoreNew core = instanceMapNew.get(network);
        if (core != null) {
            log.warn("Disconnecting existing instance: {}", core.toString());
            disconnectInstance(core);
        }

        core = new HokanCoreNew(config, this);
        try {
            instanceMapNew.put(network, core);
            core.connect("HokanCld");
            return true;
        } catch (Exception e) {
            log.error("Connect error", e);
            instanceMapNew.remove(network);
            return false;
        }

    }

    @Override
    public boolean networkJoinChannel(String network, String channel) {
        HokanCoreNew core = instanceMapNew.get(network);
        if (core != null) {
            core.joinChannel(channel);
            return true;
        }
        return false;
    }

    @Override
    public boolean putOffline(IrcServerConfigModel config) {
        String network = config.getNetwork();
        HokanCoreNew core = instanceMapNew.remove(network);
        if (core != null) {
            disconnectInstance(core);
            return true;
        } else {
            log.warn("Not online core for network: {}", network);
            return false;
        }
    }

    @Override
    public HokanCoreNew findTargetCoreByNetwork(String network) {
        for (HokanCoreNew core : instanceMapNew.values()) {
            if (core.getNetwork().equalsIgnoreCase(network)) {
                return core;
            }
        }
        return null;
    }

    @Override
    public HokanCoreNew findTargetCore(String target) {
        for (HokanCoreNew core : instanceMapNew.values()) {
            if (core.getUniqueIdent().equals(target)) {
                return core;
            }
        }
        return null;
    }

    private void disconnectInstance(HokanCoreNew instance) {
        try {
            instance.disconnect();
//            instance.dispose();
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
    public void coreDisconnected(HokanCoreNew hokanCore) {
        disconnectInstance(hokanCore);
    }

    @Override
    public void coreConnected(HokanCoreNew hokanCore) {
        connectionManager.coreConnected(hokanCore);
    }
}
