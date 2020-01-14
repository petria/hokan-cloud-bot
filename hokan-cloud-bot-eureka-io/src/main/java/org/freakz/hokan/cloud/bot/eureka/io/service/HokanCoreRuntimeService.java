package org.freakz.hokan.cloud.bot.eureka.io.service;

import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.ircengine.HokanCore;

public interface HokanCoreRuntimeService {

    boolean putOnline(IrcServerConfigModel config);

    boolean putOffline(IrcServerConfigModel config);

    HokanCore findTargetCore(String target);

    void publishRawIRCEvent(RawIRCEvent event);

    boolean networkJoinChannel(String network, String channel);

    // Events Core -> Service
    void coreConnected(HokanCore hokanCore);

    void coreDisconnected(HokanCore hokanCore);

}
