package org.freakz.hokan.cloud.bot.eureka.io.ircengine;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan.cloud.bot.common.model.event.RawIRCEvent;
import org.freakz.hokan.cloud.bot.common.model.io.IrcServerConfigModel;
import org.freakz.hokan.cloud.bot.eureka.io.service.HokanCoreRuntimeService;
import org.jibble.pircbot.PircBot;

@Slf4j
public class HokanCore extends PircBot {

    private final IrcServerConfigModel ircServerConfig;
    private final HokanCoreRuntimeService hokanCoreRuntimeService;

    public HokanCore(IrcServerConfigModel ircServerConfig, String botName, HokanCoreRuntimeService hokanCoreRuntimeService) {

        this.ircServerConfig = ircServerConfig;
        this.hokanCoreRuntimeService = hokanCoreRuntimeService;

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

    @Override
    protected void onDisconnect() {
        hokanCoreRuntimeService.coreDisconnected(this);
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message, byte[] original) {
        super.onMessage(channel, sender, login, hostname, message, original);

        RawIRCEvent event = builderEvent(this.ircServerConfig.getUniqueIdent(), "MESSAGE")
                .parameter(channel)
                .parameter(sender)
                .parameter(login)
                .parameter(hostname)
                .parameter(message);

        hokanCoreRuntimeService.publishRawIRCEvent(event);
    }

    private RawIRCEvent builderEvent(String uniqueIdent, String type) {
        return new RawIRCEvent(uniqueIdent, type);
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message, byte[] original) {
        super.onPrivateMessage(sender, login, hostname, message, original);
    }
}
