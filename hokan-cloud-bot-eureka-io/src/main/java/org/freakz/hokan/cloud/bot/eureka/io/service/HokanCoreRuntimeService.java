package org.freakz.hokan.cloud.bot.eureka.io.service;

import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;

public interface HokanCoreRuntimeService {

    boolean putOnline(IrcServerConfigModel config);

    boolean putOffline(IrcServerConfigModel config);

}
