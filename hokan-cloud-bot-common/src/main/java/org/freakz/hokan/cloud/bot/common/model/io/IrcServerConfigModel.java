package org.freakz.hokan.cloud.bot.common.model.io;

import lombok.Data;

@Data
public class IrcServerConfigModel {

    private String network;

    private String server;
    private String serverPassword;
    private int port;

    private boolean connectOnStartup;
    private boolean doReconnect;

    public String getUniqueIdent() {
        return String.format("%s-%s-%d", network, server, port);
    }

}
