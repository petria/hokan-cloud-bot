package org.freakz.hokan.cloud.bot.eureka.io.service;

import org.freakz.hokan.cloud.bot.common.model.event.MessageToIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.ChannelModel;
import org.freakz.hokan.cloud.bot.common.model.io.ChannelUserModel;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.ircengine.HokanCore;

import java.util.List;

public interface ConnectionManager {

    List<IrcServerConfigModel> getConfiguredIRCServers();

    boolean putOnline(String network);

    boolean putOffline(String network);

    boolean sendMessageToIRC(MessageToIRCEvent messageToIRCEvent);

    // EVENT handlers
    void coreConnected(HokanCore hokanCore);

    void publishRawIRCEvent(RawIRCEvent event);

    // IrcEngine commands
    List<ChannelModel> getJoinedChannels(String network);

    List<ChannelUserModel> getChannelUsers(String network, String channel);

    boolean sendWhoChannel(String network, String channel);

}
