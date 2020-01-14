package org.freakz.hokan.cloud.bot.eureka.io.service;

import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.event.ToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.ircengine.HokanCore;

import java.util.List;

public interface ConnectionManager {

    List<IrcServerConfigModel> getConfiguredIRCServers();

    boolean putOnline(String network);

    boolean putOffline(String network);

    boolean sendMessageToIRC(ToIRCEvent toIRCEvent);

    // EVENT handlers
    void coreConnected(HokanCore hokanCore);

    void publishRawIRCEvent(RawIRCEvent event);
}
