package org.freakz.hokan.cloud.bot.eureka.io.ircengine;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.jibble.pircbot.PircBot;

@Slf4j
public class HokanCore extends PircBot {

    private final IrcServerConfigModel ircServerConfig;

    public HokanCore(IrcServerConfigModel ircServerConfig, String botName) {
        this.ircServerConfig = ircServerConfig;
        setVerbose(true);
        setName(botName);
        setVersion("0.0.1");
        setLogin("hokan_cloud");
        setMessageDelay(1100);
    }

    @Override
    public void log(String message) {
        if (!message.contains("PING") && !message.contains("PONG")) {
            log.info(message);
        }
    }


    @Override
    protected void onConnect() {
        log.debug("Connected!");
    }
}
