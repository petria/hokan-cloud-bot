package org.freakz.hokan.cloud.bot.eureka.io.service;

import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.ircengine.HokanCoreNew;

public interface HokanCoreRuntimeService {

    boolean putOnline(IrcServerConfigModel config);

    boolean putOffline(IrcServerConfigModel config);

    HokanCoreNew findTargetCoreByNetwork(String network);

    HokanCoreNew findTargetCore(String target);

    void publishRawIRCEvent(RawIRCEvent event);

    boolean networkJoinChannel(String network, String channel);

    // Events Core -> Service
    void coreConnected(HokanCoreNew hokanCore);

    void coreDisconnected(HokanCoreNew hokanCore);

}
